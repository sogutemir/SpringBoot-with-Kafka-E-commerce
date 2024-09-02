package com.food.ordering.system.springwork3.product.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(Long productId) {
    super("Product with id " + productId + " not found");
    log.error("ProductNotFoundException: product with id {} not found", productId);
  }
}
