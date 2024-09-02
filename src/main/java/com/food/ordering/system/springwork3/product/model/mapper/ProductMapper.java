package com.food.ordering.system.springwork3.product.model.mapper;

import com.food.ordering.system.springwork3.cartItem.model.mapper.CartItemMapper;
import com.food.ordering.system.springwork3.order.model.mapper.OrderMapper;
import com.food.ordering.system.springwork3.product.model.ProductStatus;
import com.food.ordering.system.springwork3.product.model.entity.Product;
import com.food.ordering.system.springwork3.product.model.dto.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .price(product.getPrice())
                .status(product.getStatus().name())
                .orders(OrderMapper.toDTOList(product.getOrders()))
                .cartItems(CartItemMapper.toDTOList(product.getCartItems()))
                .deleted(product.isDeleted())
                .createdAt(product.getCreatedAt())
                .build();
    }

    public static Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .stockQuantity(productDTO.getStockQuantity())
                .price(productDTO.getPrice())
                .status(ProductStatus.valueOf(productDTO.getStatus()))
                .orders(OrderMapper.toEntityList(productDTO.getOrders()))
                .cartItems(CartItemMapper.toEntityList(productDTO.getCartItems()))
                .deleted(productDTO.isDeleted())
                .createdAt(productDTO.getCreatedAt())
                .build();
    }

    public static List<ProductDTO> toDTOList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Product> toEntityList(List<ProductDTO> productDTOs) {
        return productDTOs.stream()
                .map(ProductMapper::toEntity)
                .collect(Collectors.toList());
    }
}

