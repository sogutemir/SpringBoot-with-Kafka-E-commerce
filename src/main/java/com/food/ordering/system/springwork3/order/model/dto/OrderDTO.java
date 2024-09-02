package com.food.ordering.system.springwork3.order.model.dto;

import com.food.ordering.system.springwork3.payment.model.dto.PaymentDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private String status;
    private Long userId;
    private List<OrderProductDTO> orderProducts;
    private PaymentDTO payment;
    private boolean deleted;
    private LocalDateTime createdAt;
}
