package com.wangby.config;/**
 * @author wangby on 2020/4/21
 */

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import javax.jms.*;

/**
 * @auth wangby on 2020/4/21
 */

@Configuration
public class GetMqFactory {

    @Bean(name = "{mqFactory}")
    public ActiveMQConnectionFactory getMqFactory() throws JMSException {
        //1,获取连接工厂
//        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
//                ActiveMQConnectionFactory.DEFAULT_USER,
//                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
//                "tcp://localhost:61616"
//        );
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                "admin",
                "admin",
                "tcp://localhost:61616"
        );

        return factory;
    }
}
