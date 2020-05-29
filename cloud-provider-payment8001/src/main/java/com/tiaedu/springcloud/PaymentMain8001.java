package com.tiaedu.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 注释：@SpringBootApplication等同于同时使用@Configuration、@EnableAutoConfiguration
 * 和 @ComponentScan及其默认属性。
 */

@SpringBootApplication
@EnableEurekaClient
public class PaymentMain8001 {
    public static void main(String[] args){
        SpringApplication.run(PaymentMain8001.class, args);
    }
}
