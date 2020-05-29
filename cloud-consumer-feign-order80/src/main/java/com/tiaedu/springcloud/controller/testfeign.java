package com.tiaedu.springcloud.controller;

import com.tiaedu.springcloud.entities.CommonResult;
import com.tiaedu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface testfeign {
//    @GetMapping(value = "/payment/get/{id}")
//    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);
}
