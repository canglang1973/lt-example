package com.canglang.activemq.jms;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationBasedExporter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageFormatException;
import javax.jms.MessageListener;

/**
 * @author leitao.
 * @time: 2017/11/21  16:18
 * @version: 1.0
 * @description:
 **/
public class JmsInvokerServiceExporter extends RemoteInvocationBasedExporter implements MessageListener, InitializingBean {
    private MessageConverter messageConverter = new SimpleMessageConverter();
    private Object proxy;
    private boolean ignoreInvalidRequests = true;

    public JmsInvokerServiceExporter() {
    }

    public void afterPropertiesSet() throws Exception {
        this.proxy = this.getProxyForService();
    }

    public void onMessage(Message message) {
        try {
            RemoteInvocation invocation = this.readRemoteInvocation(message);
            if(invocation != null) {
                this.invokeAndCreateResult(invocation, this.proxy);
            }
        } catch (JMSException var3) {
            var3.printStackTrace();
        }

    }

    protected RemoteInvocation readRemoteInvocation(Message requestMessage) throws JMSException {
        Object content = this.messageConverter.fromMessage(requestMessage);
        return content instanceof RemoteInvocation?(RemoteInvocation)content:this.onInvalidRequest(requestMessage);
    }

    protected RemoteInvocation onInvalidRequest(Message requestMessage) throws JMSException {
        if(this.ignoreInvalidRequests) {
            if(this.logger.isWarnEnabled()) {
                this.logger.warn("Invalid request message will be discarded: " + requestMessage);
            }

            return null;
        } else {
            throw new MessageFormatException("Invalid request message: " + requestMessage);
        }
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = (MessageConverter)(messageConverter != null?messageConverter:new SimpleMessageConverter());
    }

    public void setIgnoreInvalidRequests(boolean ignoreInvalidRequests) {
        this.ignoreInvalidRequests = ignoreInvalidRequests;
    }
}

