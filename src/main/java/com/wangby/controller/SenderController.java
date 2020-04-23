package com.wangby.controller;/**
 * @author wangby on 2020/4/21
 */

import com.wangby.sender.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.JMSException;

/**
 * @auth wangby on 2020/4/21
 */
@Controller
@ResponseBody
public class SenderController {

    @Autowired
    SenderQueue senderQueue;
    @Autowired
    SenderTopic senderTopic;
    @Autowired
    ConfirmSenderQueue confirmSenderQueue;
    @Autowired
    TemporaryQueue temporaryQueue;
    @Autowired
    CidQueue cidQueue;

    @RequestMapping("/sendQueue")
    public String sendQueue() throws JMSException {
        senderQueue.send();
        return "success";
    }

    @RequestMapping("/receiveQueue")
    public String receive() throws JMSException {
        senderQueue.consumer("receiveQueue");
        return "success";
    }

    @RequestMapping("/receiveQueue2")
    public String receiveQueue2() throws JMSException {
        senderQueue.consumer("receiveQueue2");
        return "success";
    }


    @RequestMapping("/sendTopic")
    public String sendTopic() throws JMSException {
        senderTopic.send();
        return "success";
    }

    @RequestMapping("/receiveTopic")
    public String receiveTopic() throws JMSException {
        senderTopic.consumer("receiveTopic");
        return "success";
    }

    @RequestMapping("/receiveTopic2")
    public String receiveTopic2() throws JMSException {
        senderTopic.consumer("receiveTopic2");
        return "success";
    }


    @RequestMapping("/sendConfirm")
    public String sendConfirm() throws JMSException {
        confirmSenderQueue.send();
        return "success";
    }

    @RequestMapping("/receiveConfirm")
    public String receiveConfirm() throws JMSException {
        confirmSenderQueue.consumer("receiveQueue");
        return "success";
    }

    @RequestMapping("/sendTemporary")
    public String sendTemporary() throws JMSException {
        temporaryQueue.send();
        return "success";
    }

    @RequestMapping("/receiveTemporary")
    public String receiveTemporary() throws JMSException {
        temporaryQueue.consumer("receiveQueue");
        return "success";
    }

    @RequestMapping("/sendCid")
    public String sendCid() throws JMSException {
        cidQueue.send();
        return "success";
    }

    @RequestMapping("/receiveCid")
    public String receiveCid() throws JMSException {
        cidQueue.consumer("receiveQueue");
        return "success";
    }
}
