package com.food.ordering.system.springwork3.cartItem.service.Impl;

import com.food.ordering.system.springwork3.cartItem.model.dto.CartItemDTO;
import com.food.ordering.system.springwork3.cartItem.model.entity.CartItem;
import com.food.ordering.system.springwork3.cartItem.model.mapper.CartItemMapper;
import com.food.ordering.system.springwork3.cartItem.model.message.CartItemMessage;
import com.food.ordering.system.springwork3.cartItem.model.repository.CartItemRepository;
import com.food.ordering.system.springwork3.cartItem.service.CartItemService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @Transactional
    public void addProductToCart(Long userId, Long productId, int quantity) {
        log.info("Checking if product with id {} exists", productId);
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        CartItemMessage message = new CartItemMessage(userId, productId, quantity, "ADD");
        kafkaTemplate.send("cart-item-topic", message);
        log.info("Product add to cart message sent to Kafka for user id {} and product id {}", userId, productId);
    }

    @Override
    @Transactional
    public void removeProductFromCart(Long userId, Long productId) {
        log.info("Checking if product with id {} exists", productId);
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        CartItemMessage message = new CartItemMessage(userId, productId, 0, "REMOVE");
        kafkaTemplate.send("cart-item-topic", message);
        log.info("Product remove from cart message sent to Kafka for user id {} and product id {}", userId, productId);
    }

    @KafkaListener(topics = "cart-item-topic", groupId = "group_id")
    @Transactional
    public void listen(CartItemMessage message) {
        log.info("Received Kafka message to process cart item: {}", message);

        User user = userRepository.findById(message.getUserId())
                .orElseThrow(() -> new UserNotFoundException(message.getUserId()));
        Product product = productRepository.findById(message.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(message.getProductId()));

        if ("ADD".equals(message.getAction())) {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(message.getQuantity());
            cartItemRepository.save(cartItem);
            log.info("Product with id {} added to cart for user id {}", message.getProductId(), message.getUserId());
        } else if ("REMOVE".equals(message.getAction())) {
            CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                    .orElseThrow(() -> new RuntimeException("Cart item not found"));
            cartItemRepository.delete(cartItem);
            log.info("Product with id {} removed from cart for user id {}", message.getProductId(), message.getUserId());
        }
    }

    @Override
    public List<CartItemDTO> getCartItemsByUserId(Long userId) {
        log.info("Fetching cart items for user id: {}", userId);
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        return CartItemMapper.toDTOList(cartItems);
    }
}
