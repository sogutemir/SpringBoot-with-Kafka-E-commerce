package com.food.ordering.system.springwork3.payment.service.impl;

import com.food.ordering.system.springwork3.order.model.OrderStatus;
import com.food.ordering.system.springwork3.order.model.dto.OrderDTO;
import com.food.ordering.system.springwork3.order.model.entity.Order;
import com.food.ordering.system.springwork3.order.model.mapper.OrderMapper;
import com.food.ordering.system.springwork3.order.model.repository.OrderRepository;
import com.food.ordering.system.springwork3.payment.model.dto.PaymentDTO;
import com.food.ordering.system.springwork3.payment.model.entity.Payment;
import com.food.ordering.system.springwork3.payment.model.mapper.PaymentMapper;
import com.food.ordering.system.springwork3.payment.model.repository.PaymentRepository;
import com.food.ordering.system.springwork3.payment.service.PaymentService;
import com.food.ordering.system.springwork3.user.model.entity.User;
import com.food.ordering.system.springwork3.user.model.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public PaymentDTO processPayment(PaymentDTO paymentDTO) {
        log.info("Processing payment for order id: {}", paymentDTO.getOrderId());

        Order order = orderRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User user = order.getUser();
        BigDecimal amount = BigDecimal.valueOf(paymentDTO.getAmount());

        if (user.getMoney().compareTo(amount) < 0) {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            log.error("Insufficient funds for user id: {}. Order id: {} has been cancelled.", user.getId(), order.getId());
            throw new RuntimeException("Insufficient funds");
        }

        user.setMoney(user.getMoney().subtract(amount));
        order.setStatus(OrderStatus.APPROVED);
        orderRepository.save(order);
        userRepository.save(user);

        Payment payment = PaymentMapper.toEntity(paymentDTO);
        payment.setOrder(order);
        payment = paymentRepository.save(payment);

        log.info("Payment processed successfully for order id: {}", order.getId());

        order.setPayment(payment);
        orderRepository.save(order);

        kafkaTemplate.send("payment-processed-topic", OrderMapper.toDTO(order));

        return PaymentMapper.toDTO(payment);
    }

    @Override
    @Transactional
    @KafkaListener(topics = "order-topic", groupId = "payment-group")
    public void handleOrderMessage(String orderMessage) {
        log.info("Received order message from Kafka: {}", orderMessage);

        try {
            OrderDTO orderDTO = objectMapper.readValue(orderMessage, OrderDTO.class);
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setOrderId(orderDTO.getId());
            paymentDTO.setAmount(orderDTO.getPayment().getAmount());
            paymentDTO.setPaymentMethod(orderDTO.getPayment().getPaymentMethod());

            processPayment(paymentDTO);
            log.info("Payment processed for order id: {}", orderDTO.getId());
        } catch (Exception e) {
            log.error("Payment failed for order message: {}", orderMessage, e);
            try {
                OrderDTO orderDTO = objectMapper.readValue(orderMessage, OrderDTO.class);
                orderDTO.setStatus(OrderStatus.ERROR.name());
                String errorOrderJson = objectMapper.writeValueAsString(orderDTO);
                kafkaTemplate.send("order-update-topic", errorOrderJson);
            } catch (Exception ex) {
                log.error("Failed to send error status update for order", ex);
            }
        }
    }


}
