package com.food.ordering.system.springwork3.order.model.mapper;

import com.food.ordering.system.springwork3.order.model.OrderStatus;
import com.food.ordering.system.springwork3.order.model.entity.Order;
import com.food.ordering.system.springwork3.order.model.dto.OrderDTO;
import com.food.ordering.system.springwork3.payment.model.mapper.PaymentMapper;
import com.food.ordering.system.springwork3.product.model.entity.Product;
import com.food.ordering.system.springwork3.user.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus().name())
                .userId(order.getUser().getId())
                .productIds(order.getProducts().stream().map(Product::getId).collect(Collectors.toList()))
                .payment(PaymentMapper.toDTO(order.getPayment()))
                .deleted(order.isDeleted())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public static Order toEntity(OrderDTO orderDTO) {
        return Order.builder()
                .id(orderDTO.getId())
                .orderDate(orderDTO.getOrderDate())
                .status(OrderStatus.valueOf(orderDTO.getStatus()))
                .user(User.builder().id(orderDTO.getUserId()).build())
                .products(orderDTO.getProductIds().stream()
                        .map(id -> Product.builder().id(id).build())
                        .collect(Collectors.toList()))  // Correctly creating a List<Product>
                .payment(PaymentMapper.toEntity(orderDTO.getPayment()))
                .deleted(orderDTO.isDeleted())
                .createdAt(orderDTO.getCreatedAt())
                .build();
    }

    public static void partialUpdate(OrderDTO orderDTO, Order order) {
        if (orderDTO.getOrderDate() != null) {
            order.setOrderDate(orderDTO.getOrderDate());
        }
        if (orderDTO.getStatus() != null) {
            order.setStatus(OrderStatus.valueOf(orderDTO.getStatus()));
        }
        if (orderDTO.getUserId() != null) {
            order.setUser(User.builder().id(orderDTO.getUserId()).build());
        }
        if (orderDTO.getProductIds() != null) {
            order.setProducts(orderDTO.getProductIds().stream()
                    .map(id -> Product.builder().id(id).build())
                    .collect(Collectors.toList()));
        }
        if (orderDTO.getPayment() != null) {
            order.setPayment(PaymentMapper.toEntity(orderDTO.getPayment()));
        }
    }


    public static List<OrderDTO> toDTOList(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Order> toEntityList(List<OrderDTO> orderDTOs) {
        return orderDTOs.stream()
                .map(OrderMapper::toEntity)
                .collect(Collectors.toList());
    }
}
