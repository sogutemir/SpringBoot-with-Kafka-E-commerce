package com.food.ordering.system.springwork3.address.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AddressDTO {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private boolean deleted;
    private LocalDateTime createdAt;
}
