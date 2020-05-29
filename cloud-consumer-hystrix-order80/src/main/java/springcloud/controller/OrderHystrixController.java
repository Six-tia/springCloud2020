package springcloud.controller;


import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springcloud.service.PaymentHystrixService;

import javax.annotation.Resource;

@RestController
@Slf4j
//此注解避免了每个方法都配一个服务降级方法（冗余）
//除个别处理方法需要单独设计，其他可以使用这一种统一处理
@DefaultProperties(defaultFallback = "global_paymentInfo_handler")
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "consumer/payment/hystrix/{id}")
    public String paymentHystrix(@PathVariable("id") Long id){
        return paymentHystrixService.paymentHystrix(id);
    }

    @GetMapping(value = "hytrix/global/error")
    //注解没有指定特定方法，使用全局方法
    @HystrixCommand
    public String paymentHystrix(){
        int age = 10/0;
        return "error divide!";
    }


    //此注解指定服务降级的处理方法(对应某个方法),不会执行全局方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            //指定超市3秒则进行降级处理（自身设置的峰值实现）
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="2000")
    })
    @GetMapping(value = "consumer/payment/hystrix/timeout/{id}")
    public String paymentHystrixTimeOut(@PathVariable("id") Long id){
        //openfeign-ribbon一般默认等待1s,超过会报错（可以自定义等待时间（yml））
        //ribbon:
        //  #指建立连接等待时间，适用于网络连接正常情况下两端连接所用时间
        //  ReadTimeout: 5000
        //  #指建立连接后从服务器读取到可用资源的时间
        //  ConnectTimeout: 5000
        //int age = 10/0;
        return paymentHystrixService.paymentHystrixTimeOut(id);
    }

    public String paymentInfo_TimeoutHandler(Long id){
        return "thread pool: " + Thread.currentThread().getName() + "payment_info id:" + id + " Timeout or Error!";
    }

    public String global_paymentInfo_handler(){
        return "thread pool: " + Thread.currentThread().getName() + " global error!";
    }

}
