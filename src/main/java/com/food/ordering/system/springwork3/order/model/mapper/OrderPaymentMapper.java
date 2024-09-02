package com.food.ordering.system.springwork3.order.model.mapper;

import com.food.ordering.system.springwork3.order.model.dto.OrderPaymentDTO;
import com.food.ordering.system.springwork3.order.model.entity.Order;
import com.food.ordering.system.springwork3.payment.model.entity.Payment;

public class OrderPaymentMapper {

    public static OrderPaymentDTO toOrderPaymentDTO(Order order) {
        Payment payment = order.getPayment();
        return OrderPaymentDTO.builder()
                .orderId(order.getId())
                .amount(payment != null ? payment.getAmount() : null)
                .paymentMethod(payment != null ? payment.getPaymentMethod() : null)
                .build();
    }

    public static Payment toPaymentEntity(OrderPaymentDTO orderPaymentDTO, Order order) {
        return Payment.builder()
                .order(order)
                .amount(orderPaymentDTO.getAmount())
                .paymentMethod(orderPaymentDTO.getPaymentMethod())
                .build();
    }
}
