package com.food.ordering.system.springwork3.order.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long orderId) {
        super("Order with id " + orderId + " not found");
        log.error("OrderNotFoundException: User with id {} not found", orderId);
    }
}
