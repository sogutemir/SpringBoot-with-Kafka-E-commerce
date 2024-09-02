package com.food.ordering.system.springwork3.cartItem.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CartItemDTO {
    private Long id;
    private int quantity;
    private Long userId;
    private Long productId;
    private boolean deleted;
    private LocalDateTime createdAt;
}
