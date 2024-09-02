package com.food.ordering.system.springwork3.cartItem.controller;

import com.food.ordering.system.springwork3.cartItem.model.dto.CartItemDTO;
import com.food.ordering.system.springwork3.cartItem.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<Void> addProductToCart(@RequestBody CartItemDTO cartItemDTO) {
        cartItemService.addProductToCart(cartItemDTO.getUserId(), cartItemDTO.getProductId(), cartItemDTO.getQuantity());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removeProductFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        cartItemService.removeProductFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDTO>> getCartItemsByUserId(@PathVariable Long userId) {
        List<CartItemDTO> cartItems = cartItemService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }
}
