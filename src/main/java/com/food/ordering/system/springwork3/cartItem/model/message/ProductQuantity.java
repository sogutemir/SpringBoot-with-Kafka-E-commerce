package com.food.ordering.system.springwork3.cartItem.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantity {
    private Long productId;
    private int quantity;
}
