package com.food.ordering.system.springwork3.cartItem.model.repository;

import com.food.ordering.system.springwork3.cartItem.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
