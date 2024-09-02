package com.food.ordering.system.springwork3.product.model.dto;

import com.food.ordering.system.springwork3.cartItem.model.dto.CartItemDTO;
import com.food.ordering.system.springwork3.order.model.dto.OrderDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private int stockQuantity;
    private double price;
    private String status;
    private List<OrderDTO> orders;
    private List<CartItemDTO> cartItems;
    private boolean deleted;
    private LocalDateTime createdAt;
}
