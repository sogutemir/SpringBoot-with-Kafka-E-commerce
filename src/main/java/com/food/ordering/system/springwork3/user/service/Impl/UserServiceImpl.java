package com.food.ordering.system.springwork3.user.service.Impl;

import com.food.ordering.system.springwork3.user.exception.UserNotFoundException;
import com.food.ordering.system.springwork3.user.exception.UserServiceException;
import com.food.ordering.system.springwork3.user.model.dto.UserDTO;
import com.food.ordering.system.springwork3.user.model.entity.User;
import com.food.ordering.system.springwork3.user.model.UserStatus;
import com.food.ordering.system.springwork3.user.model.mapper.UserMapper;
import com.food.ordering.system.springwork3.user.model.repository.UserRepository;
import com.food.ordering.system.springwork3.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating user with email: {}", userDTO.getEmail());
        try {
            User user = UserMapper.toEntity(userDTO);
            user = userRepository.save(user);
            return UserMapper.toDTO(user);
        } catch (Exception e) {
            log.error("Error occurred while creating user", e);
            throw new UserServiceException("User creation failed", e);
        }
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        log.info("Updating user with id: {}", userId);
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                throw new UserNotFoundException(userId);
            }
            User user = optionalUser.get();
            UserMapper.partialUpdate(userDTO, user);
            user = userRepository.save(user);
            return UserMapper.toDTO(user);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating user", e);
            throw new UserServiceException("User update failed", e);
        }
    }

    @Override
    public UserDTO getUserById(Long userId) {
        log.info("Fetching user with id: {}", userId);
        return userRepository.findById(userId)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return UserMapper.toDTOList(userRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        log.info("Deleting user with id: {}", userId);
        try {
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException(userId);
            }
            userRepository.deleteById(userId);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while deleting user", e);
            throw new UserServiceException("User deletion failed", e);
        }
    }

    @Override
    public List<UserDTO> getUsersByStatus(UserStatus status) {
        log.info("Fetching users with status: {}", status);
        try {
            List<User> users = userRepository.findByStatus(status);
            return UserMapper.toDTOList(users);
        } catch (Exception e) {
            log.error("Error occurred while fetching users by status", e);
            throw new UserServiceException("Failed to fetch users by status", e);
        }
    }
}
