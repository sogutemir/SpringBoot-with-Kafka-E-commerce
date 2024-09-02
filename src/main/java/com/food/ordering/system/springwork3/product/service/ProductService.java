package com.food.ordering.system.springwork3.product.service;

import com.food.ordering.system.springwork3.product.model.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
    ProductDTO getProductById(Long productId);
    List<ProductDTO> getAllProducts();
    void deleteProduct(Long productId);
}
