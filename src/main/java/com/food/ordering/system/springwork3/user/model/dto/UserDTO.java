package com.food.ordering.system.springwork3.user.model.dto;

import com.food.ordering.system.springwork3.address.model.dto.AddressDTO;
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
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String status;
    private AddressDTO address;
    private List<OrderDTO> orders;
    private List<CartItemDTO> cartItems;
    private boolean deleted;
    private LocalDateTime createdAt;
}
