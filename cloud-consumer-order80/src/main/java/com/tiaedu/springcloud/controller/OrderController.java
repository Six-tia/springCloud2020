package com.tiaedu.springcloud.controller;

import com.tiaedu.springcloud.entities.CommonResult;
import com.tiaedu.springcloud.entities.Payment;
import com.tiaedu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE/payment";
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Integer> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/create", payment, CommonResult.class);

    }

    @GetMapping("/consumer/payment/createEntity")
    public ResponseEntity<CommonResult> createEntity(Payment payment){

        return restTemplate.postForEntity(PAYMENT_URL+"/create", payment, CommonResult.class);

    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/get/"+id, CommonResult.class);
    }

    //未使用ribbon技术，使用自定义的负载均衡类
    @GetMapping("/consumer/payment/getlb/{id}")
    public CommonResult<Payment> getPaymentLb(@PathVariable("id") Long id){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size() <= 0){
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.instance(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri+"/payment/get/"+id, CommonResult.class);
    }


    @GetMapping("/consumer/payment/getentity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/get/" + id, CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult<>(444, "get for entity error");
       }
    }

//    @GetMapping("/consumer/payment/getentity/{id}")
//    public ResponseEntity<CommonResult> getPayment2(@PathVariable("id") Long id){
//        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/get/" + id, CommonResult.class);
//        if(entity.getStatusCode().is2xxSuccessful()){
//            return entity;
//        }else{
//            return null;
//        }
//    }


}
