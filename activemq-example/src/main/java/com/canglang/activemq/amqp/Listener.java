package com.canglang.activemq.amqp;

import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.*;

/**
 * @author leitao.
 * @time: 2017/11/23  10:14
 * @version: 1.0
 * @description: amqp协议示例
 **/
public class Listener {
    public static void main(String[] args) throws JMSException {

        final String TOPIC_PREFIX = "topic://";

        String user = "admin";
        String password ="admin";
        String host = "localhost";
        int port = 5672;

        String connectionURI = "amqp://" + host + ":" + port;
        String destinationName = arg(args, 0, "queue://event");
//        String destinationName = arg(args, 0, "topic://event");

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

        MessageConsumer consumer = session.createConsumer(destination);
        long start = System.currentTimeMillis();
        long count = 1;
        System.out.println("Waiting for messages...");
        while (true) {
            Message msg = consumer.receive();
            if (msg instanceof TextMessage) {
                String body = ((TextMessage) msg).getText();
                if ("SHUTDOWN".equals(body)) {
                    long diff = System.currentTimeMillis() - start;
                    System.out.println(String.format("Received %d in %.2f seconds", count, (1.0 * diff / 1000.0)));
                    connection.close();
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {}
                    System.exit(1);
                } else {
                    try {
                        if (count != msg.getIntProperty("id")) {
                            System.out.println("mismatch: " + count + "!=" + msg.getIntProperty("id"));
                        }
                    } catch (NumberFormatException ignore) {
                    }

                    if (count == 1) {
                        start = System.currentTimeMillis();
                    } else if (count % 1000 == 0) {
                        System.out.println(String.format("Received %d messages.", count));
                    }
                    count++;
                }

            } else {
                System.out.println("Unexpected message type: " + msg.getClass());
            }
        }
    }

    /**
     * 获取环境变量
     * @param key
     * @param defaultValue
     * @return
     */
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
