package com.springCloud.paymentService.service.impl;

import com.springCloud.paymentService.entity.Payment;
import com.springCloud.paymentService.repository.PaymentRepository;
import com.springCloud.paymentService.service.PaymentService;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment doPayment(Payment payment) {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    public String paymentProcessing(){
        return new Random().nextBoolean()?"success":"false";
    }
}
