package com.wangby.sender;
/**
 * @author wangby on 2020/4/21
 */

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.xml.soap.Text;
import java.util.Enumeration;

/**
 * @auth wangby on 2020/4/21
 */
@Service
public class ConfirmSenderQueue {

    @Qualifier("{mqFactory}")
    @Autowired
    ActiveMQConnectionFactory factory;

    public void send() throws JMSException {
        //2,获取一个activeMq连接
        ActiveMQConnection connection = (ActiveMQConnection) factory.createConnection();
        connection.start();
        //3,获取session
        /**
         * 第一个参数表示是否开启事务
         * 第二个参数表示确认机制
         */

        QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        //5，消息创建者
//        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
//        producer.setTimeToLive(1000*3);
        //5.2，创建消息
        for (int i = 0; i < 3; i++) {
            QueueRequestor requestor = new QueueRequestor(session, new ActiveMQQueue("user"));
            System.out.println("==准备发送请求");
            TextMessage msg = (TextMessage) requestor.request(session.createTextMessage("hi"));
            System.out.println("==请求发送完了");
            //System.out.println("responseMsg: " + msg.getText());
            System.out.println("system exit....");

//            TextMessage textMsg = session.createTextMessage("hi: " + i);
//            textMsg.setLongProperty("aaa", 123);
//            //5.3，向目的地写入消息
//            producer.send(textMsg);

//            session.commit();

//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
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
        //5, 创建消费者

        MessageConsumer consumer = session.createConsumer(new ActiveMQQueue("user"));

        //添加消息监听
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("收到消息：" + ((TextMessage)message).getText());

                    //开始确认消息
                    Destination jmsReplyTo = message.getJMSReplyTo();
                    System.out.println("jmsReplyTo: " + jmsReplyTo);
                    MessageProducer producer = session.createProducer(jmsReplyTo);
                    producer.send(session.createTextMessage("xxx..."));
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
