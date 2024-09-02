package com.food.ordering.system.springwork3.cartItem.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    private Long id;
    private int quantity;
    private Long userId;
    private Long productId;
    private boolean deleted;
    private LocalDateTime createdAt;
}
