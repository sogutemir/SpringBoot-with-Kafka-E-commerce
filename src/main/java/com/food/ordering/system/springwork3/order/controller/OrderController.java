package com.food.ordering.system.springwork3.order.controller;

import com.food.ordering.system.springwork3.order.model.dto.OrderDTO;
import com.food.ordering.system.springwork3.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        log.info("Received request to create order for user id: {}", orderDTO.getUserId());
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        log.info("Received request to get order with id: {}", orderId);
        OrderDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        log.info("Received request to cancel order with id: {}", orderId);
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-status/{orderId}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        log.info("Received request to update order status for order id: {} to {}", orderId, status);
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.noContent().build();
    }
}
