package com.food.ordering.system.springwork3.cartItem.model.repository;

import com.food.ordering.system.springwork3.cartItem.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.ordering.system.springwork3.product.model.entity.Product;
import com.food.ordering.system.springwork3.user.model.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.user = :user AND ci.product = :product")
    Optional<CartItem> findByUserAndProduct(User user, Product product);


    @Query("SELECT ci FROM CartItem ci WHERE ci.user.id = :userId")
    List<CartItem> findByUserId(Long userId);
}
