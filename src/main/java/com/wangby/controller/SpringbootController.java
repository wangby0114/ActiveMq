package com.wangby.controller;

import com.wangby.constant.Constant;
import com.wangby.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auth wangby on 2020/4/22
 */
@Controller
@ResponseBody
public class SpringbootController {

    @Autowired
    private SendService sendService;

    @RequestMapping("/send1")
    public String send1() {
        sendService.send1(Constant.DESTINATION, "send1_hi activeMq!");

        return "success";
    }

    @RequestMapping("/send2")
    public String send2() {
        sendService.send2(Constant.DESTINATION, "send2_hi activeMq!");

        return "success";
    }

    @RequestMapping("/send3")
    public String send3() {
        sendService.send2(Constant.DESTINATION, "send3_hi activeMq!");

        return "success";
    }
}
