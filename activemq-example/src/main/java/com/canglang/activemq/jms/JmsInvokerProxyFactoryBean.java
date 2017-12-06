package com.canglang.activemq.jms;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

import java.io.Serializable;

/**
 * @author leitao.
 * @time: 2017/11/21  16:18
 * @version: 1.0
 * @description:
 **/
public class JmsInvokerProxyFactoryBean extends JmsInvokerClientInterceptor implements FactoryBean<Object>, Serializable {
    private static final long serialVersionUID = -7757782016385604820L;
    private Class<?> serviceInterface;
    private Object serviceProxy;

    public JmsInvokerProxyFactoryBean() {
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        if(serviceInterface != null && serviceInterface.isInterface()) {
            this.serviceInterface = serviceInterface;
        } else {
            throw new IllegalArgumentException("'serviceInterface' must be an interface");
        }
    }

    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        if(this.serviceInterface == null) {
            throw new IllegalArgumentException("Property 'serviceInterface' is required");
        } else {
            this.serviceProxy = (new ProxyFactory(this.serviceInterface, this)).getProxy();
        }
    }

    public Object getObject() {
        return this.serviceProxy;
    }

    public Class<?> getObjectType() {
        return this.serviceInterface;
    }

    public boolean isSingleton() {
        return true;
    }
}

