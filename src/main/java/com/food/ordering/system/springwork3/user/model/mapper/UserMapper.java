package com.food.ordering.system.springwork3.user.model.mapper;

import com.food.ordering.system.springwork3.address.model.mapper.AddressMapper;
import com.food.ordering.system.springwork3.cartItem.model.mapper.CartItemMapper;
import com.food.ordering.system.springwork3.order.model.mapper.OrderMapper;
import com.food.ordering.system.springwork3.user.model.UserStatus;
import com.food.ordering.system.springwork3.user.model.entity.User;
import com.food.ordering.system.springwork3.user.model.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus().name())
                .address(AddressMapper.toDTO(user.getAddress()))
                .orders(OrderMapper.toDTOList(user.getOrders()))
                .cartItems(CartItemMapper.toDTOList(user.getCartItems()))
                .deleted(user.isDeleted())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .status(UserStatus.valueOf(userDTO.getStatus()))
                .address(AddressMapper.toEntity(userDTO.getAddress()))
                .orders(OrderMapper.toEntityList(userDTO.getOrders()))
                .cartItems(CartItemMapper.toEntityList(userDTO.getCartItems()))
                .deleted(userDTO.isDeleted())
                .createdAt(userDTO.getCreatedAt())
                .build();
    }

    public static void partialUpdate(UserDTO userDTO, User user) {
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());
        }
        if (userDTO.getStatus() != null) {
            user.setStatus(UserStatus.valueOf(userDTO.getStatus()));
        }
        if (userDTO.getAddress() != null) {
            user.setAddress(AddressMapper.toEntity(userDTO.getAddress()));
        }
        if (userDTO.getOrders() != null) {
            user.setOrders(OrderMapper.toEntityList(userDTO.getOrders()));
        }
        if (userDTO.getCartItems() != null) {
            user.setCartItems(CartItemMapper.toEntityList(userDTO.getCartItems()));
        }
    }


    public static List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<User> toEntityList(List<UserDTO> userDTOs) {
        return userDTOs.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }
}
