package com.food.ordering.system.springwork3.user.service;

import com.food.ordering.system.springwork3.user.model.dto.UserDTO;
import com.food.ordering.system.springwork3.user.model.UserStatus;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    UserDTO getUserById(Long userId);
    List<UserDTO> getAllUsers();
    void deleteUser(Long userId);
    List<UserDTO> getUsersByStatus(UserStatus status);
}
