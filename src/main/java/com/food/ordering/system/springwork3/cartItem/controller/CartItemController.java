package com.food.ordering.system.springwork3.cartItem.controller;

import com.food.ordering.system.springwork3.cartItem.model.dto.CartItemDTO;
import com.food.ordering.system.springwork3.cartItem.model.message.ProductQuantity;
import com.food.ordering.system.springwork3.cartItem.service.CartItemService;
import com.food.ordering.system.springwork3.order.model.dto.OrderDTO;
import com.food.ordering.system.springwork3.payment.model.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
@Slf4j
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<Void> addProductsToCart(@RequestBody List<ProductQuantity> productQuantities, @RequestParam Long userId) {
        cartItemService.addProductsToCart(userId, productQuantities);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderDTO> createOrderFromCart(
            @RequestParam Long userId,
            @RequestBody PaymentDTO paymentDTO) {
        log.info("Creating order from cart for user id: {}", userId);
        OrderDTO orderDTO = cartItemService.createOrderFromCart(userId, paymentDTO);
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removeProductsFromCart(@RequestBody List<ProductQuantity> productQuantities, @RequestParam Long userId) {
        cartItemService.removeProductsFromCart(userId, productQuantities);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDTO>> getCartItemsByUserId(@PathVariable Long userId) {
        List<CartItemDTO> cartItems = cartItemService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }
}
