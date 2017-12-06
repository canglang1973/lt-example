package com.canglang.activemq.openwire.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author leitao.
 * @time: 2017/11/6  14:44
 * @version: 1.0
 * @description:
 **/
public class MethodInvokeMqConsumer {

    public static void main(String[] args){
        new ClassPathXmlApplicationContext("spring-core.xml", "activemq/openwire/activemq-consumer.xml");

    }
}
