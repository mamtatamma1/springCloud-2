package com.springCloud.orderService.service.impl;

import com.springCloud.orderService.config.CloudConfig;
import com.springCloud.orderService.entity.Order;
import com.springCloud.orderService.entity.Payment;
import com.springCloud.orderService.entity.TransactionRequest;
import com.springCloud.orderService.entity.TransactionResponse;
import com.springCloud.orderService.repository.OrderRepository;
import com.springCloud.orderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Autowired
    private CloudConfig cloudConfig;

    @Override
    public TransactionResponse saveOrder(TransactionRequest transactionRequest) {
        Order order = transactionRequest.getOrder();
        Payment payment = transactionRequest.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
//        rest call
//        Payment paymentResponse = restTemplate.postForObject("http://PAYMENT-SERVICE/api/v1/payment/doPayment", payment, Payment.class);
        Payment paymentResponse = restTemplate.postForObject(cloudConfig.getPAYMENT_ENDPOINT_URL(), payment, Payment.class);
        String message = paymentResponse.getPaymentStatus().equals("success") ? "Order placed successfully" : "Payment failed. order added to cart";
        Order savedOrder = orderRepository.save(order);
        return new TransactionResponse(savedOrder, paymentResponse.getAmount(), paymentResponse.getTransactionId(), message);
    }
}
