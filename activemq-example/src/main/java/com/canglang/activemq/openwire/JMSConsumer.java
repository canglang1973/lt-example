package com.canglang.activemq.openwire;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author leitao.
 * @time: 2017/11/6  10:14
 * @version: 1.0
 * @description:
 **/
public class JMSConsumer {
    private static  final  String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static  final  String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static  final  String BROKEURL  = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] agrs){
        ConnectionFactory connectionFactory;
        Connection connection =null;
        Session session;
        MessageConsumer messageConsumer;
        Destination destination;
        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEURL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("hello");
            messageConsumer = session.createConsumer(destination);
            while (true){
                TextMessage textMessage = (TextMessage) messageConsumer.receive(1000);
                textMessage.acknowledge();
                if (textMessage!=null){
                    System.out.println("收到的消息是:"+textMessage.getText());
                }else {
                    break;
                }
            }
        }catch (Exception e){
        }finally {
            try {
                if (connection!=null) {
                    connection.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
