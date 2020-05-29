package springcloud.service.impl;

import org.springframework.stereotype.Component;
import springcloud.service.PaymentHystrixService;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentHystrix(Long id) {
        return "paymentHystrix fallback!";
    }

    @Override
    public String paymentHystrixTimeOut(Long id) {
        return "paymentHystrix timeout fallback!";
    }
}
