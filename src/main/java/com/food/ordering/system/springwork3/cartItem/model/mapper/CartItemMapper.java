package com.food.ordering.system.springwork3.cartItem.model.mapper;

import com.food.ordering.system.springwork3.cartItem.model.entity.CartItem;
import com.food.ordering.system.springwork3.cartItem.model.dto.CartItemDTO;
import com.food.ordering.system.springwork3.product.model.entity.Product;
import com.food.ordering.system.springwork3.user.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemMapper {

    public static CartItemDTO toDTO(CartItem cartItem) {
        return CartItemDTO.builder()
                .id(cartItem.getId())
                .userId(cartItem.getUser().getId())
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .deleted(cartItem.isDeleted())
                .createdAt(cartItem.getCreatedAt())
                .build();
    }

    public static CartItem toEntity(CartItemDTO cartItemDTO) {
        return CartItem.builder()
                .id(cartItemDTO.getId())
                .user(cartItemDTO.getUserId() != null ? User.builder().id(cartItemDTO.getUserId()).build() : null)
                .product(cartItemDTO.getProductId() != null ? Product.builder().id(cartItemDTO.getProductId()).build() : null)
                .quantity(cartItemDTO.getQuantity())
                .deleted(cartItemDTO.isDeleted())
                .createdAt(cartItemDTO.getCreatedAt())
                .build();
    }

    public static void partialUpdate(CartItemDTO cartItemDTO, CartItem cartItem) {
        if (cartItemDTO.getUserId() != null) {
            cartItem.setUser(User.builder().id(cartItemDTO.getUserId()).build());
        }
        if (cartItemDTO.getProductId() != null) {
            cartItem.setProduct(Product.builder().id(cartItemDTO.getProductId()).build());
        }
        if (cartItemDTO.getQuantity() != 0) {
            cartItem.setQuantity(cartItemDTO.getQuantity());
        }
        cartItem.setDeleted(cartItemDTO.isDeleted());
        cartItem.setCreatedAt(cartItemDTO.getCreatedAt());
    }

    public static List<CartItemDTO> toDTOList(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<CartItem> toEntityList(List<CartItemDTO> cartItemDTOs) {
        return cartItemDTOs.stream()
                .map(CartItemMapper::toEntity)
                .collect(Collectors.toList());
    }
}
