package com.food.ordering.system.springwork3.address.service;

import com.food.ordering.system.springwork3.address.model.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(Long userId, AddressDTO addressDTO);
    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);
    AddressDTO getAddressById(Long addressId);
    List<AddressDTO> getAllAddresses();
    void deleteAddress(Long addressId);
}
