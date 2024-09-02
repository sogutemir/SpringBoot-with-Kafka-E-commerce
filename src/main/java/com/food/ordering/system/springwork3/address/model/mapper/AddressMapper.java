package com.food.ordering.system.springwork3.address.model.mapper;

import com.food.ordering.system.springwork3.address.model.entity.Address;
import com.food.ordering.system.springwork3.address.model.dto.AddressDTO;

import java.util.List;
import java.util.stream.Collectors;

public class AddressMapper {

    public static AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .deleted(address.isDeleted())
                .createdAt(address.getCreatedAt())
                .build();
    }

    public static Address toEntity(AddressDTO addressDTO) {
        return Address.builder()
                .id(addressDTO.getId())
                .street(addressDTO.getStreet())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .zipCode(addressDTO.getZipCode())
                .deleted(addressDTO.isDeleted())
                .createdAt(addressDTO.getCreatedAt())
                .build();
    }

    public static List<AddressDTO> toDTOList(List<Address> addresses) {
        return addresses.stream()
                .map(AddressMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Address> toEntityList(List<AddressDTO> addressDTOs) {
        return addressDTOs.stream()
                .map(AddressMapper::toEntity)
                .collect(Collectors.toList());
    }
}
