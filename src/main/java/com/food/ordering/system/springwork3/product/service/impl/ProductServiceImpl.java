package com.food.ordering.system.springwork3.product.service.impl;

import com.food.ordering.system.springwork3.product.model.dto.ProductDTO;
import com.food.ordering.system.springwork3.product.model.entity.Product;
import com.food.ordering.system.springwork3.product.model.mapper.ProductMapper;
import com.food.ordering.system.springwork3.product.model.repository.ProductRepository;
import com.food.ordering.system.springwork3.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        log.info("Creating product with name: {}", productDTO.getName());
        try {
            Product product = ProductMapper.toEntity(productDTO);
            product = productRepository.save(product);
            return ProductMapper.toDTO(product);
        } catch (Exception e) {
            log.error("Error occurred while creating product", e);
            throw new RuntimeException("Product creation failed", e);
        }
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        log.info("Updating product with id: {}", productId);
        try {
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isEmpty()) {
                log.error("Product with id {} not found", productId);
                throw new RuntimeException("Product not found");
            }
            Product product = optionalProduct.get();
            ProductMapper.partialUpdate(productDTO, product);
            product = productRepository.save(product);
            return ProductMapper.toDTO(product);
        } catch (Exception e) {
            log.error("Error occurred while updating product", e);
            throw new RuntimeException("Product update failed", e);
        }
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        log.info("Fetching product with id: {}", productId);
        return productRepository.findById(productId)
                .map(ProductMapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Product with id {} not found", productId);
                    return new RuntimeException("Product not found");
                });
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        log.info("Fetching all products");
        return ProductMapper.toDTOList(productRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        log.info("Deleting product with id: {}", productId);
        try {
            if (!productRepository.existsById(productId)) {
                log.error("Product with id {} not found", productId);
                throw new RuntimeException("Product not found");
            }
            productRepository.deleteById(productId);
        } catch (Exception e) {
            log.error("Error occurred while deleting product", e);
            throw new RuntimeException("Product deletion failed", e);
        }
    }
}
