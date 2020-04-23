package com.wangby.listener;/**
 * @author wangby on 2020/4/22
 */

import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @auth wangby on 2020/4/22
 */
public class MsgListener implements MessageListener {
    private String receiver;

    public MsgListener() {}

    public MsgListener(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage msg = (TextMessage) message;
        try {
            String result;
            if (StringUtils.isEmpty(receiver)) {
                result = msg.getText();
            } else {
                result = receiver + msg.getText();
            }
            System.out.println(result);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
