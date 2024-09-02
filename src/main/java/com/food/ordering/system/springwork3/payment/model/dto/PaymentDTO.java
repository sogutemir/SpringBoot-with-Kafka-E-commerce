package com.food.ordering.system.springwork3.payment.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentDTO {
    private Long id;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private double amount;
    private Long orderId;
    private boolean deleted;
    private LocalDateTime createdAt;
}
