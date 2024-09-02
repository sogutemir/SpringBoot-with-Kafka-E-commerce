package com.food.ordering.system.springwork3.address.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
