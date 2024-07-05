package com.springCloud.paymentService.service;

import com.springCloud.paymentService.entity.Payment;

public interface PaymentService {

    Payment doPayment(Payment payment);

    Payment findPaymentHistoryByOrderId(Integer orderId);
}
