package com.food.ordering.system.springwork3.payment.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
