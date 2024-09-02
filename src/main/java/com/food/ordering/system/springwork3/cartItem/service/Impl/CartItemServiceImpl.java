package com.food.ordering.system.springwork3.cartItem.service.Impl;

import com.food.ordering.system.springwork3.cartItem.model.dto.CartItemDTO;
import com.food.ordering.system.springwork3.cartItem.model.entity.CartItem;
import com.food.ordering.system.springwork3.cartItem.model.mapper.CartItemMapper;
import com.food.ordering.system.springwork3.cartItem.model.message.CartItemMessage;
import com.food.ordering.system.springwork3.cartItem.model.message.ProductQuantity;
import com.food.ordering.system.springwork3.cartItem.model.repository.CartItemRepository;
import com.food.ordering.system.springwork3.cartItem.service.CartItemService;
import com.food.ordering.system.springwork3.order.model.OrderStatus;
import com.food.ordering.system.springwork3.order.model.dto.OrderDTO;
import com.food.ordering.system.springwork3.order.model.dto.OrderProductDTO;
import com.food.ordering.system.springwork3.order.service.OrderService;
import com.food.ordering.system.springwork3.payment.model.dto.PaymentDTO;
import com.food.ordering.system.springwork3.product.exception.ProductNotFoundException;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @Transactional
    public void addProductsToCart(Long userId, List<ProductQuantity> productQuantities) {
        productQuantities.forEach(productQuantity -> {
            log.info("Checking if product with id {} exists", productQuantity.getProductId());
            Optional<Product> product = productRepository.findById(productQuantity.getProductId());

            if (product.isEmpty()) {
                throw new ProductNotFoundException(productQuantity.getProductId());
            }
        });

        CartItemMessage message = new CartItemMessage(userId, productQuantities, "ADD");
        kafkaTemplate.send("cart-item-topic", message);
        log.info("Products add to cart message sent to Kafka for user id {}", userId);
    }

    @Transactional
    @Override
    public OrderDTO createOrderFromCart(Long userId, PaymentDTO paymentDTO) {
        log.info("Creating order from cart for user id: {}", userId);

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("No items found in cart for user id: " + userId);
        }

        List<OrderProductDTO> orderProductDTOs = cartItems.stream()
                .map(CartItemMapper::toOrderProductDTO)
                .collect(Collectors.toList());

        OrderDTO orderDTO = OrderDTO.builder()
                .userId(userId)
                .orderProducts(orderProductDTOs)
                .payment(paymentDTO)
                .status(OrderStatus.PENDING.name())
                .build();

        return orderService.createOrder(orderDTO);
    }

    @Override
    @Transactional
    public void removeProductsFromCart(Long userId, List<ProductQuantity> productQuantities) {
        productQuantities.forEach(productQuantity -> {
            log.info("Checking if product with id {} exists", productQuantity.getProductId());
            Optional<Product> product = productRepository.findById(productQuantity.getProductId());

            if (product.isEmpty()) {
                throw new ProductNotFoundException(productQuantity.getProductId());
            }
        });

        CartItemMessage message = new CartItemMessage(userId, productQuantities, "REMOVE");
        kafkaTemplate.send("cart-item-topic", message);
        log.info("Products remove from cart message sent to Kafka for user id {}", userId);
    }

    @KafkaListener(topics = "cart-item-topic", groupId = "group_id")
    @Transactional
    public void listen(CartItemMessage message) {
        log.info("Received Kafka message to process cart items: {}", message);

        User user = userRepository.findById(message.getUserId())
                .orElseThrow(() -> new UserNotFoundException(message.getUserId()));

        for (ProductQuantity productQuantity : message.getProducts()) {
            Product product = productRepository.findById(productQuantity.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(productQuantity.getProductId()));

            if ("ADD".equals(message.getAction())) {
                CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                        .orElse(new CartItem());
                cartItem.setUser(user);
                cartItem.setProduct(product);
                cartItem.setQuantity(cartItem.getQuantity() + productQuantity.getQuantity());
                cartItemRepository.save(cartItem);
                log.info("Product with id {} added to cart for user id {}", productQuantity.getProductId(), message.getUserId());
            } else if ("REMOVE".equals(message.getAction())) {
                CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                        .orElseThrow(() -> new RuntimeException("Cart item not found"));
                cartItemRepository.delete(cartItem);
                log.info("Product with id {} removed from cart for user id {}", productQuantity.getProductId(), message.getUserId());
            }
        }
    }

    @Override
    public List<CartItemDTO> getCartItemsByUserId(Long userId) {
        log.info("Fetching cart items for user id: {}", userId);
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        return CartItemMapper.toDTOList(cartItems);
    }
}
