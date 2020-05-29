package com.tiaedu.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tiaedu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 在高并发环境下，对一个服务调用频度过高，同一层次其他接口被困死
 * 因为tomcat线程池中的工作线程被占满
 * （限流 容错 降级）
 */


@RestController
//@Slf4j注解等同于以下代码
//private static final Logger log =
// LoggerFactory.getLogger(SummerGiftController.class);
// 加入此注解后，可以直接使用log.info(),log.error()......
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/hystrix/{id}")
    public String paymentInfo(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo(id);
        log.info("result:"+result);
        return result;
    }


    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_error(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_Timeout(id);
        log.info("result:"+result);
        return result;
    }

    //服务熔断
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result = paymentService.PaymentCircuitBreaker(id);
        log.info("-----result:" + result);
        return result;
    }

}
