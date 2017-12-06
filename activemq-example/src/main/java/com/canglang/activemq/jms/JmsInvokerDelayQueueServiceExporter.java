package com.canglang.activemq.jms;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationBasedExporter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageFormatException;
import javax.jms.MessageListener;
import java.lang.reflect.Method;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author leitao.
 * @time: 2017/11/21  16:17
 * @version: 1.0
 * @description:
 **/
public class JmsInvokerDelayQueueServiceExporter extends RemoteInvocationBasedExporter implements MessageListener, InitializingBean {
    private static final Log LOG = LogFactory.getLog(JmsInvokerDelayQueueServiceExporter.class);
    private final DelayQueue<DelayItem<RemoteInvocation>> delayQueue = new DelayQueue();
    private final ArrayBlockingQueue<IdempotentKey> arrayBlockingQueue = new ArrayBlockingQueue(1000);
    private final ConcurrentHashMap<IdempotentKey, DelayItem<RemoteInvocation>> idempotentMap = new ConcurrentHashMap();
    private final IdempotentKey NULL = new IdempotentKey();
    private Thread daemonThread;
    private long timeOut = 60L;
    private MessageConverter messageConverter = new SimpleMessageConverter();
    private Object proxy;
    private boolean ignoreInvalidRequests = true;

    public JmsInvokerDelayQueueServiceExporter() {
        Runnable daemonTask = new Runnable() {
            public void run() {
                JmsInvokerDelayQueueServiceExporter.this.daemonCheck();
            }
        };
        this.daemonThread = new Thread(daemonTask);
        this.daemonThread.setDaemon(true);
        this.daemonThread.setName("JmsInvokerDelayQueueServiceExporter Daemon");
        this.daemonThread.start();
    }

    private void daemonCheck() {
        while(true) {
            try {
                DelayItem<RemoteInvocation> delayItem = (DelayItem)this.delayQueue.take();
                if(delayItem != null) {
                    RemoteInvocation invocation = (RemoteInvocation)delayItem.getItem();
                    this.invokeAndCreateResult(invocation, this.proxy);
                    Object key = this.arrayBlockingQueue.take();
                    if(key != null && !key.equals(this.NULL)) {
                        this.idempotentMap.remove(key);
                    }

                    if(this.arrayBlockingQueue.isEmpty()) {
                        this.idempotentMap.clear();
                    }
                }
            } catch (InterruptedException var4) {
                LOG.error(var4.getMessage(), var4);
                return;
            }
        }
    }

    public void afterPropertiesSet() throws Exception {
        this.proxy = this.getProxyForService();
    }

    public void onMessage(Message message) {
        RemoteInvocation invocation = null;

        try {
            invocation = this.readRemoteInvocation(message);
            if(invocation != null) {
                DelayItem<RemoteInvocation> delayItem = new DelayItem(invocation, TimeUnit.NANOSECONDS.convert(this.timeOut, TimeUnit.SECONDS));
                Method method = this.getService().getClass().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                Idempotent inIdempotent = (Idempotent)method.getAnnotation(Idempotent.class);
                IdempotentKey key = null;
                if(inIdempotent != null && invocation.getArguments().length > inIdempotent.identifierPosition()) {
                    key = new IdempotentKey(method, invocation.getArguments()[inIdempotent.identifierPosition()]);
                    if(this.idempotentMap.get(key) != null) {
                        this.delayQueue.remove(this.idempotentMap.get(key));
                        this.arrayBlockingQueue.remove(key);
                    }

                    this.idempotentMap.put(key, delayItem);
                }

                this.delayQueue.put(delayItem);
                if(key != null) {
                    this.arrayBlockingQueue.put(key);
                } else {
                    this.arrayBlockingQueue.put(this.NULL);
                }
            }
        } catch (Exception var7) {
            LOG.error(var7.getMessage(), var7);
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

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }
}

