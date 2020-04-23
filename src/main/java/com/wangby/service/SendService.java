package com.wangby.service;
/**
 * @author wangby on 2020/4/22
 */

import com.wangby.constant.Constant;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @auth wangby on 2020/4/22
 */

@Service
public class SendService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send1(String destinationNam, String msg) {

        jmsMessagingTemplate.convertAndSend(destinationNam, msg);
        System.out.println("发送消息：" + msg);
    }

    public void send2(String destinationNam, String msg) {
        ConnectionFactory factory = jmsTemplate.getConnectionFactory();
        Connection connection = null;
        try {
            connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(destinationNam);
            MessageProducer producer = session.createProducer(destination);

            for (int i = 0; i < 20; i++) {
                TextMessage textMessage = session.createTextMessage(msg + "_" + i);
                producer.send(textMessage);
                System.out.println("发送消息：" + textMessage.getText());
            }

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }

    public void send3(String destinationNam, String msg) {
        List<String> list = new ArrayList<String>();
        list.add("123");
        list.add("456");
        list.add("789");


        jmsMessagingTemplate.convertAndSend(new ActiveMQQueue(destinationNam), list);
        System.out.println("发送消息：" + msg);
    }
}
