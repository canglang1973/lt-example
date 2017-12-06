package com.canglang.activemq.jms;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.support.DefaultRemoteInvocationFactory;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationFactory;

import javax.jms.*;
import javax.jms.IllegalStateException;

/**
 * @author leitao.
 * @time: 2017/11/21  16:14
 * @version: 1.0
 * @description:
 **/
public class JmsInvokerClientInterceptor implements MethodInterceptor, InitializingBean {
    private ConnectionFactory connectionFactory;
    private Object destination;
    private DestinationResolver destinationResolver = new DynamicDestinationResolver();
    private RemoteInvocationFactory remoteInvocationFactory = new DefaultRemoteInvocationFactory();
    private MessageConverter messageConverter = new SimpleMessageConverter();

    public JmsInvokerClientInterceptor() {
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    protected ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setDestinationName(String destinationName) {
        this.destination = destinationName;
    }

    public void setDestinationResolver(DestinationResolver destinationResolver) {
        this.destinationResolver = (DestinationResolver)(destinationResolver != null?destinationResolver:new DynamicDestinationResolver());
    }

    public void setRemoteInvocationFactory(RemoteInvocationFactory remoteInvocationFactory) {
        this.remoteInvocationFactory = (RemoteInvocationFactory)(remoteInvocationFactory != null?remoteInvocationFactory:new DefaultRemoteInvocationFactory());
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = (MessageConverter)(messageConverter != null?messageConverter:new SimpleMessageConverter());
    }

    public void afterPropertiesSet() {
        if(this.getConnectionFactory() == null) {
            throw new IllegalArgumentException("Property 'connectionFactory' is required");
        } else if(this.destination == null) {
            throw new IllegalArgumentException("'destination' or 'destinationName' is required");
        }
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if(this.destination != null && this.connectionFactory != null) {
            if(AopUtils.isToStringMethod(methodInvocation.getMethod())) {
                return "JMS invoker proxy for destination [" + this.destination + "]";
            } else {
                RemoteInvocation invocation = this.createRemoteInvocation(methodInvocation);

                try {
                    this.executeRequest(invocation);
                    return "no return";
                } catch (JMSException var4) {
                    throw this.convertJmsInvokerAccessException(var4);
                }
            }
        } else {
            return null;
        }
    }

    protected RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation) {
        return this.remoteInvocationFactory.createRemoteInvocation(methodInvocation);
    }

    protected void executeRequest(RemoteInvocation invocation) throws JMSException {
        Connection con = this.createConnection();
        Session session = null;

        try {
            session = this.createSession(con);
            Destination destination = this.resolveDestination(session);
            Message requestMessage = this.createRequestMessage(session, invocation);
            con.start();
            this.doExecuteRequest(session, destination, requestMessage);
        } finally {
            JmsUtils.closeSession(session);
            ConnectionFactoryUtils.releaseConnection(con, this.getConnectionFactory(), true);
        }

    }

    protected Connection createConnection() throws JMSException {
        ConnectionFactory cf = this.getConnectionFactory();
        return cf.createConnection();
    }

    protected Session createSession(Connection con) throws JMSException {
        return con.createSession(false, 1);
    }

    protected Destination resolveDestination(Session session) throws JMSException {
        if(this.destination instanceof Destination) {
            return (Destination)this.destination;
        } else if(this.destination instanceof String) {
            return this.resolveDestinationName(session, (String)this.destination);
        } else {
            throw new IllegalStateException("Queue object [" + this.destination + "] is neither a [javax.jms.Destination] nor a destination name String");
        }
    }

    protected Destination resolveDestinationName(Session session, String queueName) throws JMSException {
        return this.destinationResolver.resolveDestinationName(session, queueName, true);
    }

    protected Message createRequestMessage(Session session, RemoteInvocation invocation) throws JMSException {
        return this.messageConverter.toMessage(invocation, session);
    }

    protected void doExecuteRequest(Session session, Destination destination, Message requestMessage) throws JMSException {
        MessageProducer producer = null;

        try {
            producer = session.createProducer(destination);
            producer.send(requestMessage);
        } finally {
            JmsUtils.closeMessageProducer(producer);
        }

    }

    protected RemoteAccessException convertJmsInvokerAccessException(JMSException ex) {
        throw new RemoteAccessException("Could not access JMS invoker destination [" + this.destination + "]", ex);
    }
}
