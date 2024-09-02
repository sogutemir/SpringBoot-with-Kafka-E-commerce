package com.food.ordering.system.springwork3.cartItem.service;

import com.food.ordering.system.springwork3.cartItem.model.dto.CartItemDTO;
import com.food.ordering.system.springwork3.cartItem.model.message.ProductQuantity;
import com.food.ordering.system.springwork3.order.model.dto.OrderDTO;
import com.food.ordering.system.springwork3.payment.model.dto.PaymentDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemService {
    void addProductsToCart(Long userId, List<ProductQuantity> productQuantities);

    @Transactional
    OrderDTO createOrderFromCart(Long userId, PaymentDTO paymentDTO);

    void removeProductsFromCart(Long userId, List<ProductQuantity> productQuantities);

    List<CartItemDTO> getCartItemsByUserId(Long userId);
}
