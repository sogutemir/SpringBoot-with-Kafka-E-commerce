package com.food.ordering.system.springwork3.order.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPaymentDTO {
    private Long orderId;
    private BigDecimal amount;
    private String paymentMethod;
}
