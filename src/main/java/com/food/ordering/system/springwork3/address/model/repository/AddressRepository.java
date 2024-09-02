package com.food.ordering.system.springwork3.address.model.repository;

import com.food.ordering.system.springwork3.address.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
