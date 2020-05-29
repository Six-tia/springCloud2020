package com.tiaedu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo(Integer id){
        return "thread pool: " + Thread.currentThread().getName() + "payment_info id:" + id;
    }

    //此注解指定服务降级的处理方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            //指定超市3秒则进行降级处理（自身设置的峰值实现）
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="3000")
    })
    public String paymentInfo_Timeout(Integer id){
        //程序出错，也会进入降级方法
        //int age = 10/0;
        int timenum = 1;
        try{
            TimeUnit.SECONDS.sleep(timenum);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return "thread pool: " + Thread.currentThread().getName() + "payment_info id:" + id + "耗时：" + timenum + "s";
    }

    public String paymentInfo_TimeoutHandler(Integer id){
        return "thread pool: " + Thread.currentThread().getName() + "payment_info id:" + id + " Timeout or Error!";
    }

    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallBack",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口期（范围）
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") //失败率达到多少后跳闸
    })
    public String PaymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id < 0){
            throw new RuntimeException("id cannot be a negative number");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallBack(@PathVariable("id") Integer id){
        return "id cannot be a negative number";
    }

}
