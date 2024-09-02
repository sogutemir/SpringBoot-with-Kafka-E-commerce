package com.food.ordering.system.springwork3.product.model.dto;

import com.food.ordering.system.springwork3.cartItem.model.dto.CartItemDTO;
import com.food.ordering.system.springwork3.order.model.dto.OrderDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
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
