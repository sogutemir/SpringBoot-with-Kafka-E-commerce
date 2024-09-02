package com.food.ordering.system.springwork3.product.model.repository;

import com.food.ordering.system.springwork3.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
