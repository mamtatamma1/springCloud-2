package com.springCloud.orderService.controller;

import com.springCloud.orderService.entity.Order;
import com.springCloud.orderService.entity.TransactionRequest;
import com.springCloud.orderService.entity.TransactionResponse;
import com.springCloud.orderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public TransactionResponse bookOrder(@RequestBody TransactionRequest transactionRequest){
        return orderService.saveOrder(transactionRequest);
    }

    @GetMapping
    public String getOrder(){
        return "Ordered";
    }

}
