package com.wangby.service;

import com.wangby.constant.Constant;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * 接收消息service
 *
 * @author wangby on 2020/4/22
 */
@Service
public class ReceiveService {

    @JmsListener(destination = "springboot", containerFactory = "jmsListenerContainerTopic")
    public void receiveTopic(String msg) {
        System.out.println("收到的消息：" + msg);
    }

    @JmsListener(destination = "springboot", containerFactory = "jmsListenerContainerQueue")
    public void receiveQueue(String msg) {
        System.out.println("收到的消息：" + msg);
    }
}
