package springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tiaedu.springcloud.entities.CommonResult;
import com.tiaedu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springcloud.service.impl.PaymentFallbackService;

/**
 * 服务降级处理的实现类，实现解耦（即避免业务方法与降级方法混在一起）
 */
@Service
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT", fallback = PaymentFallbackService.class)
public interface PaymentHystrixService {
    @GetMapping(value = "/payment/hystrix/{id}")
    String paymentHystrix(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    String paymentHystrixTimeOut(@PathVariable("id") Long id);
}
