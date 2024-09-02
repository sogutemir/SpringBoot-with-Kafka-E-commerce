package com.food.ordering.system.springwork3.product.model.mapper;

import com.food.ordering.system.springwork3.common.mapper.BigDecimalMapper;
import com.food.ordering.system.springwork3.product.model.ProductStatus;
import com.food.ordering.system.springwork3.product.model.dto.ProductDTO;
import com.food.ordering.system.springwork3.product.model.entity.Product;
import com.food.ordering.system.springwork3.order.model.mapper.OrderProductMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(BigDecimalMapper.toDouble(product.getPrice()))
                .stockQuantity(product.getStockQuantity())
                .status(product.getStatus().name())
                .orderProducts(product.getOrderProducts() != null
                        ? OrderProductMapper.toDTOList(product.getOrderProducts())
                        : null)
                .deleted(product.isDeleted())
                .createdAt(product.getCreatedAt())
                .build();
    }

    public static Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(BigDecimalMapper.fromDouble(productDTO.getPrice()))
                .stockQuantity(productDTO.getStockQuantity())
                .status(ProductStatus.valueOf(productDTO.getStatus()))
                .orderProducts(productDTO.getOrderProducts() != null
                        ? OrderProductMapper.toEntityList(productDTO.getOrderProducts())
                        : null)
                .deleted(productDTO.isDeleted())
                .createdAt(productDTO.getCreatedAt())
                .build();
    }

    public static void partialUpdate(ProductDTO productDTO, Product product) {
        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (productDTO.getPrice() != 0) {
            product.setPrice(BigDecimalMapper.fromDouble(productDTO.getPrice()));
        }
        if (productDTO.getStockQuantity() != 0) {
            product.setStockQuantity(productDTO.getStockQuantity());
        }
        if (productDTO.getStatus() != null) {
            product.setStatus(ProductStatus.valueOf(productDTO.getStatus()));
        }
        if (productDTO.getOrderProducts() != null) {
            product.setOrderProducts(OrderProductMapper.toEntityList(productDTO.getOrderProducts()));
        }
        product.setDeleted(productDTO.isDeleted());
        product.setCreatedAt(productDTO.getCreatedAt());
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
