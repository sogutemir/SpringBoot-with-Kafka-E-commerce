package com.food.ordering.system.springwork3.user.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceException extends RuntimeException {

    public UserServiceException(String message) {
        super(message);
        log.error("UserServiceException: {}", message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
        log.error("UserServiceException: {}", message, cause);
    }
}
