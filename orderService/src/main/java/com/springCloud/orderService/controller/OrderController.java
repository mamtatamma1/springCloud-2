package com.springCloud.orderService.controller;

import com.springCloud.orderService.config.CloudConfig;
import com.springCloud.orderService.entity.Order;
import com.springCloud.orderService.entity.TransactionRequest;
import com.springCloud.orderService.entity.TransactionResponse;
import com.springCloud.orderService.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
/*
 * @RefreshScope To mark class, beans and specified components
 * to be refreshed for newer configurations
 * call this API to make changes: POST -> localhost:8085/actuator/refresh
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CloudConfig cloudConfig;

    @GetMapping
    public String getProp() {
//        String payment_url = cloudConfig.getPAYMENT_ENDPOINT_URL();
        String order_url = cloudConfig.getORDER_ENDPOINT_URL();
        return "payment_url = " + "payment_url" + "order_url = " + order_url;
    }

    @PostMapping
    @CircuitBreaker(name = "orderPaymentCB", fallbackMethod = "orderPaymentFallback")
//    @Retry(name = "orderPaymentRetry", fallbackMethod = "orderPaymentFallback")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest transactionRequest) {
        return orderService.saveOrder(transactionRequest);
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
        return transactionResponse;
    }
}
