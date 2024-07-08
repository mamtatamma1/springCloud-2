package com.springCloud.orderService.controller;

import com.springCloud.orderService.entity.Order;
import com.springCloud.orderService.entity.TransactionRequest;
import com.springCloud.orderService.entity.TransactionResponse;
import com.springCloud.orderService.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @CircuitBreaker(name = "orderPaymentCB", fallbackMethod = "orderPaymentFallback")
//    @Retry(name = "orderPaymentRetry", fallbackMethod = "orderPaymentFallback")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest transactionRequest){
        return orderService.saveOrder(transactionRequest);
    }

    @GetMapping
    public String getOrder(){
        return "Ordered";
    }

    public TransactionResponse orderPaymentFallback(Exception e) {
        Order order = new Order();
        order.setName("order1");
        order.setQty(2);
        order.setPrice(2000);
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setOrder(order);
        transactionResponse.setAmount(2000);
        transactionResponse.setTransactionId(UUID.randomUUID().toString());
        transactionResponse.setResponseMessage("fallback order");
        return transactionResponse;    }
}
