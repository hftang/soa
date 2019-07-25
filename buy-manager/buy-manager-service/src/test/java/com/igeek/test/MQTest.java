package com.igeek.test;

import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTempQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.transport.stomp.Stomp;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.io.IOException;

/**
 * @author hftang
 * @date 2019-07-24 10:06
 * @desc
 */
public class MQTest {

//    @Test
    public void testQueueProducer() throws JMSException {

        ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory("tcp://192.168.217.132:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("igeek-queue");
        MessageProducer producer = session.createProducer(destination);

        TextMessage message = session.createTextMessage("这是我发送的测试消息 this is test msg");

        producer.send(message);

        producer.close();
        session.close();
        connection.close();


    }

//    @Test
    public void testQueueReceive() throws JMSException {
        ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory("tcp://192.168.217.132:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("igeek-queue");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });

        consumer.close();
        session.close();
        connection.close();


    }

    //topic 订阅模式的发送和接受
    //发送端
//    @Test
    public void testTopicSend() throws JMSException {
        ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory("tcp://192.168.217.132:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic("igeek-topic");
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage("发送一个测试消息---->--->");
        producer.send(message);

        producer.close();
        session.close();
        connection.close();


    }

    //接受端
//    @Test
    public void testTopicReceive() throws JMSException, IOException {
        ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory("tcp://192.168.217.132:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic("igeek-topic");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage msg = (TextMessage) message;
                try {
                    System.out.println("接受3号接受到的消息：" + "" + msg.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();

        consumer.close();
        session.close();
        connection.close();

    }

    /***
     * 测试jms在spring中的使用
     */
//    @Test
    public void testJms() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        ActiveMQQueue queue = context.getBean(ActiveMQQueue.class);
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage text = session.createTextMessage("this is new msg");
                return text;
            }
        });
    }

//    @Test
    public void testJmstopic() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        ActiveMQTopic queue = context.getBean(ActiveMQTopic.class);
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage text = session.createTextMessage("this is new msg");
                return text;
            }
        });
    }

}
