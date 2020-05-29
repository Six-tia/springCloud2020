package com.tiaedu.springcloud.controller;


import com.tiaedu.springcloud.entities.CommonResult;
import com.tiaedu.springcloud.entities.Payment;
import com.tiaedu.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "consumer/payment/feign/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "consumer/payment/feign/timeout")
    public String paymentFeignTimeOut(){
        //openfeign-ribbon一般默认等待1s,超过会报错（可以自定义等待时间（yml））
        //ribbon:
        //  #指建立连接等待时间，适用于网络连接正常情况下两端连接所用时间
        //  ReadTimeout: 5000
        //  #指建立连接后从服务器读取到可用资源的时间
        //  ConnectTimeout: 5000
        return paymentFeignService.paymentFeignTimeOut();
    };
}
