package com.food.ordering.system.springwork3.cartItem.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemMessage {
    private Long userId;
    private Long productId;
    private int quantity;
    private String action;
}