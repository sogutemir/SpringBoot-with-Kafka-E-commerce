package com.food.ordering.system.springwork3.order.model.mapper;

import com.food.ordering.system.springwork3.order.model.dto.OrderProductDTO;
import com.food.ordering.system.springwork3.order.model.entity.OrderProduct;
import com.food.ordering.system.springwork3.product.model.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class OrderProductMapper {

    public static OrderProductDTO toDTO(OrderProduct orderProduct) {
        return OrderProductDTO.builder()
                .productId(orderProduct.getProduct().getId())
                .quantity(orderProduct.getQuantity())
                .build();
    }

    public static OrderProduct toEntity(OrderProductDTO orderProductDTO) {
        return OrderProduct.builder()
                .product(Product.builder().id(orderProductDTO.getProductId()).build())
                .quantity(orderProductDTO.getQuantity())
                .build();
    }

    public static List<OrderProductDTO> toDTOList(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(OrderProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<OrderProduct> toEntityList(List<OrderProductDTO> orderProductDTOs) {
        return orderProductDTOs.stream()
                .map(OrderProductMapper::toEntity)
                .collect(Collectors.toList());
    }
}
