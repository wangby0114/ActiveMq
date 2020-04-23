package com.wangby.sender;
/**
 * @author wangby on 2020/4/21
 */

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Random;

/**
 * @auth wangby on 2020/4/21
 */
@Service
public class CidQueue {

    @Qualifier("{mqFactory}")
    @Autowired
    ActiveMQConnectionFactory factory;

    public void send() throws JMSException {
        //2,获取一个activeMq连接
        Connection connection = factory.createConnection();
        connection.start();
        //3,获取session
        /**
         * 第一个参数表示是否开启事务
         * 第二个参数表示确认机制
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue user = session.createQueue("user");
        MessageProducer producer = session.createProducer(user);

        TextMessage message = session.createTextMessage("hello");
        String cid = String.valueOf(new Random().nextInt());
        message.setJMSCorrelationID(cid);
        message.setStringProperty("type", "p");

        System.out.println("==开始发送消息");
        producer.send(message);
        System.out.println("==开始完成");

        //监听temporaryQueue回调
        MessageConsumer consumer = session.createConsumer(user, "cid = '" + cid + "' and type = 'c'");
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("==收到监听");
            }
        });
    }

    public void consumer(String cons) throws JMSException {
        //2,获取一个activeMq连接
        Connection connection = factory.createConnection();
        connection.start();

        //3,获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(new ActiveMQQueue("user"), "type = 'p'");

        //添加消息监听
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    String textMsg = ((TextMessage)message).getText();
                    System.out.println("收到消息：" + textMsg);

                    //开始确认消息
                    MessageProducer producer = session.createProducer(new ActiveMQQueue("user"));
                    TextMessage msg = session.createTextMessage("asdfas");
                    msg.setStringProperty("cid", message.getJMSCorrelationID());
                    msg.setStringProperty("type", "c");
                    producer.send(msg);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

//        //6，关闭连接
//
//        System.out.println("发送消息成功");
    }
}
