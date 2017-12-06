package com.canglang.activemq.amqp;

import org.apache.qpid.jms.*;
import javax.jms.*;
/**
 * @author leitao.
 * @time: 2017/11/23  10:05
 * @version: 1.0
 * @description: amqp协议示例
 **/
public class Publisher {
    public static void main(String[] args) throws Exception {

        final String TOPIC_PREFIX = "topic://";

        String user = "admin";
        String password ="admin";
        String host = "localhost";
        int port = 5672;

        String connectionURI = "amqp://" + host + ":" + port;
        String destinationName = arg(args, 0, "queue://event");
//        String destinationName = arg(args, 0, "topic://event");

        int messages = 10000;
        int size = 256;

        String DATA = "abcdefghijklmnopqrstuvwxyz";
        String body = "";
        for (int i = 0; i < size; i++) {
            body += DATA.charAt(i % DATA.length());
        }

        JmsConnectionFactory factory = new JmsConnectionFactory(connectionURI);

        Connection connection = factory.createConnection(user, password);
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = null;
        if (destinationName.startsWith(TOPIC_PREFIX)) {
            destination = session.createTopic(destinationName.substring(TOPIC_PREFIX.length()));
        } else {
            destination = session.createQueue(destinationName);
        }

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        for (int i = 1; i <= messages; i++) {
            TextMessage msg = session.createTextMessage("#:" + i);
            msg.setIntProperty("id", i);
            producer.send(msg);
            if ((i % 1000) == 0) {
                System.out.println(String.format("Sent %d messages", i));
            }
        }

        producer.send(session.createTextMessage("SHUTDOWN"));
        Thread.sleep(1000 * 3);
        connection.close();
        System.exit(0);
    }

    private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if (rc == null) {
            return defaultValue;
        }
        return rc;
    }

    private static String arg(String[] args, int index, String defaultValue) {
        if (index < args.length) {
            return args[index];
        }
        else {
            return defaultValue;
        }
    }
}
