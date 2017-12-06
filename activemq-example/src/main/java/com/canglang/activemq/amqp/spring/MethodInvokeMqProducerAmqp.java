package com.canglang.activemq.amqp.spring;

import com.canglang.activemq.MethodInvokeMq;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author leitao.
 * @time: 2017/11/6  14:44
 * @version: 1.0
 * @description:
 **/
public class MethodInvokeMqProducerAmqp {

    public static void main(String[] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("activemq/amqp/activemq-producer-amqp.xml");
        final MethodInvokeMq methodInvokeMq = applicationContext.getBean("methodInvokeMqProcessor", MethodInvokeMq.class);
        final MethodInvokeMq methodInvokeMq_topic = applicationContext.getBean("methodInvokeMqProcessor_topic", MethodInvokeMq.class);
        for (int i=0;i<10;i++){
            methodInvokeMq.printMesg("canglang_queue_amqp"+i);
            methodInvokeMq_topic.printMesg("canglang_topic_amqp"+i);
        }
        System.out.println("===========END===========");

    }
}
