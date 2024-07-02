package com.springCloud.orderService.service;

import com.springCloud.orderService.entity.Order;
import com.springCloud.orderService.entity.TransactionRequest;
import com.springCloud.orderService.entity.TransactionResponse;

public interface OrderService {

    TransactionResponse saveOrder(TransactionRequest transactionRequest);
}
