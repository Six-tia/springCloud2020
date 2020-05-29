package com.tiaedu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/payment/zk")
    public String paymentZk(){
        //UUID(Universally Unique Identifier)全局唯一标识符,
        //是指在一台机器上生成的数字，它保证对在同一时空中的所有机器都是唯一的。
        return "springcloudwithzookeeper" + serverPort + "\t" + UUID.randomUUID().toString();
    }
}
