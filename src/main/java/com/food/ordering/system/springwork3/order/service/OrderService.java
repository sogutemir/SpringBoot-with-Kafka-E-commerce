package com.food.ordering.system.springwork3.order.service;

import com.food.ordering.system.springwork3.order.model.dto.OrderDTO;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO getOrderById(Long orderId);

    void cancelOrder(Long orderId);

    void updateOrderStatus(Long orderId, String status);
}
