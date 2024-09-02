package com.food.ordering.system.springwork3.order.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductDTO {
    private Long productId;
    private int quantity;
}
