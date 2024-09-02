package com.food.ordering.system.springwork3.cartItem.service;

import com.food.ordering.system.springwork3.cartItem.model.dto.CartItemDTO;

import java.util.List;

public interface CartItemService {
    void addProductToCart(Long userId, Long productId, int quantity);
    void removeProductFromCart(Long userId, Long productId);
    List<CartItemDTO> getCartItemsByUserId(Long userId);
}