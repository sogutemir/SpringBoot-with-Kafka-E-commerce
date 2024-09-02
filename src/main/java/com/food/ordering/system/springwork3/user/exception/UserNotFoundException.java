package com.food.ordering.system.springwork3.user.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("User with id " + userId + " not found");
        log.error("UserNotFoundException: User with id {} not found", userId);
    }
}
