package com.wangby.sender;/**
 * @author wangby on 2020/4/21
 */

import com.wangby.listener.MsgListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * @auth wangby on 2020/4/21
 */
@Service
public class SenderTopic {

    @Qualifier("{mqFactory}")
    @Autowired
    ActiveMQConnectionFactory factory;

    public void send() throws JMSException {
        //2,获取一个activeMq连接
        Connection connection = factory.createConnection();

        //3,获取session
        /**
         * 第一个参数表示是否开启事务
         * 第二个参数表示确认机制
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4，找目的地，获取destination,消费者也从这个目的地获取消息去消费
        Destination topic = session.createTopic("user");
        //5，消息创建者
        MessageProducer producer = session.createProducer(topic);
        //5.2，创建消息
        for (int i = 0; i < 20; i++) {
            TextMessage textMsg = session.createTextMessage("hi: " + i);
            //5.3，向目的地写入消息
            producer.send(textMsg);

//            session.commit();

//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            System.out.println("发送：" +  textMsg.getText());
        }

        //6，关闭连接
        connection.close();

        System.out.println("发送消息成功");
    }

    public void consumer(String cons) throws JMSException {
        //2,获取一个activeMq连接
        Connection connection = factory.createConnection();
        connection.start();

        //3,获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4，找目的地，获取destination,消费者也从这个目的地获取消息去消费
        Destination topic = session.createTopic("user");
        //5, 创建消费者
        MessageConsumer consumer = session.createConsumer(topic);
        MsgListener msgListener = new MsgListener(cons);
        consumer.setMessageListener(msgListener);


        //6，关闭连接
//        connection.close();

    }
}
