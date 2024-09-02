package com.food.ordering.system.springwork3.address.service.Impl;

import com.food.ordering.system.springwork3.address.model.dto.AddressDTO;
import com.food.ordering.system.springwork3.address.model.entity.Address;
import com.food.ordering.system.springwork3.address.model.mapper.AddressMapper;
import com.food.ordering.system.springwork3.address.model.repository.AddressRepository;
import com.food.ordering.system.springwork3.address.service.AddressService;
import com.food.ordering.system.springwork3.user.exception.UserNotFoundException;
import com.food.ordering.system.springwork3.user.model.entity.User;
import com.food.ordering.system.springwork3.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AddressDTO createAddress(Long userId, AddressDTO addressDTO) {
        log.info("Creating address for user with id: {}", userId);
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                throw new UserNotFoundException(userId);
            }
            User user = optionalUser.get();
            Address address = AddressMapper.toEntity(addressDTO);
            address.setUser(user);
            address = addressRepository.save(address);
            user.setAddress(address);
            userRepository.save(user);
            return AddressMapper.toDTO(address);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while creating address for user id: {}", userId, e);
            throw new RuntimeException("Address creation failed", e);
        }
    }

    @Override
    @Transactional
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        log.info("Updating address with id: {}", addressId);
        try {
            Optional<Address> optionalAddress = addressRepository.findById(addressId);
            if (optionalAddress.isEmpty()) {
                log.error("Address with id {} not found", addressId);
                throw new RuntimeException("Address not found");
            }
            Address address = optionalAddress.get();
            AddressMapper.partialUpdate(addressDTO, address);
            address = addressRepository.save(address);
            return AddressMapper.toDTO(address);
        } catch (Exception e) {
            log.error("Error occurred while updating address id: {}", addressId, e);
            throw new RuntimeException("Address update failed", e);
        }
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        log.info("Fetching address with id: {}", addressId);
        return addressRepository.findById(addressId)
                .map(AddressMapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Address with id {} not found", addressId);
                    return new RuntimeException("Address not found");
                });
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        log.info("Fetching all addresses");
        return AddressMapper.toDTOList(addressRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteAddress(Long addressId) {
        log.info("Deleting address with id: {}", addressId);
        try {
            if (!addressRepository.existsById(addressId)) {
                log.error("Address with id {} not found", addressId);
                throw new RuntimeException("Address not found");
            }
            addressRepository.deleteById(addressId);
        } catch (Exception e) {
            log.error("Error occurred while deleting address id: {}", addressId, e);
            throw new RuntimeException("Address deletion failed", e);
        }
    }
}
