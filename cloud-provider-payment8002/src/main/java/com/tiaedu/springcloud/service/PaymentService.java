package com.tiaedu.springcloud.service;

import com.tiaedu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(Long id);
}
