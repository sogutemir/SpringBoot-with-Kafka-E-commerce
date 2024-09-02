package com.food.ordering.system.springwork3.order.model.dto;

import com.food.ordering.system.springwork3.payment.model.dto.PaymentDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private String status;
    private Long userId;
    private List<Long> productIds;
    private PaymentDTO payment;
    private boolean deleted;
    private LocalDateTime createdAt;
}
