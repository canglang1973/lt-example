package com.canglang.activemq.stomp.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author leitao.
 * @time: 2017/11/6  14:44
 * @version: 1.0
 * @description:
 **/
public class MethodInvokeMqConsumerStomp {

    public static void main(String[] args){
        new ClassPathXmlApplicationContext("spring-core.xml", "activemq/stomp/activemq-consumer-stomp.xml");

    }
}
