package com.food.ordering.system.springwork3.order.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springwork3.order.exception.OrderNotFoundException;
import com.food.ordering.system.springwork3.order.model.OrderStatus;
import com.food.ordering.system.springwork3.order.model.dto.OrderDTO;
import com.food.ordering.system.springwork3.order.model.entity.Order;
import com.food.ordering.system.springwork3.order.model.entity.OrderProduct;
import com.food.ordering.system.springwork3.order.model.mapper.OrderMapper;
import com.food.ordering.system.springwork3.order.model.repository.OrderRepository;
import com.food.ordering.system.springwork3.order.service.OrderService;
import com.food.ordering.system.springwork3.payment.model.entity.Payment;
import com.food.ordering.system.springwork3.payment.model.repository.PaymentRepository;
import com.food.ordering.system.springwork3.product.model.entity.Product;
import com.food.ordering.system.springwork3.product.model.repository.ProductRepository;
import com.food.ordering.system.springwork3.user.exception.UserNotFoundException;
import com.food.ordering.system.springwork3.user.model.entity.User;
import com.food.ordering.system.springwork3.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        log.info("Creating order for user id: {}", orderDTO.getUserId());

        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(orderDTO.getUserId()));

        Order order = OrderMapper.toEntity(orderDTO);
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            Product product = productRepository.findById(orderProduct.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStockQuantity() < orderProduct.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            BigDecimal productTotal = product.getPrice().multiply(BigDecimal.valueOf(orderProduct.getQuantity()));
            totalAmount = totalAmount.add(productTotal);
        }

        if (order.getPayment() == null) {
            order.setPayment(new Payment());
        }
        Payment payment = order.getPayment();
        payment.setAmount(totalAmount);
        payment.setOrder(order);

        order = orderRepository.save(order);

        paymentRepository.save(payment);

        try {
            String orderJson = objectMapper.writeValueAsString(OrderMapper.toDTO(order));
            kafkaTemplate.send("order-topic", orderJson);
        } catch (Exception e) {
            log.error("Failed to send order to Kafka", e);
            throw new RuntimeException("Failed to send order to Kafka", e);
        }

        log.info("Order created with id: {} and sent to Kafka", order.getId());

        return OrderMapper.toDTO(order);
    }



    @Override
    public OrderDTO getOrderById(Long orderId) {
        log.info("Fetching order with id: {}", orderId);
        return orderRepository.findById(orderId)
                .map(OrderMapper::toDTO)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        log.info("Cancelling order with id: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (order.getStatus() == OrderStatus.APPROVED) {
            throw new RuntimeException("Cannot cancel an approved order");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        log.info("Order with id: {} has been cancelled", orderId);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        log.info("Updating order status for order id: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);
        log.info("Order status for order id: {} has been updated to {}", orderId, status);
    }

    @Transactional
    @KafkaListener(topics = "order-update-topic", groupId = "order-service-group")
    public void handleOrderUpdateMessage(String orderMessage) {
        log.info("Received order update message from Kafka: {}", orderMessage);

        try {
            OrderDTO orderDTO = objectMapper.readValue(orderMessage, OrderDTO.class);
            Long orderId = orderDTO.getId();
            String newStatus = orderDTO.getStatus();

            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException(orderId));

            if (newStatus != null) {
                order.setStatus(OrderStatus.valueOf(newStatus));
                orderRepository.save(order);
                log.info("Order status for order id: {} has been updated to {}", orderId, newStatus);
            } else {
                log.warn("No status found in the update message for order id: {}", orderId);
            }
        } catch (Exception e) {
            log.error("Failed to update order status from message: {}", orderMessage, e);
        }
    }

}
