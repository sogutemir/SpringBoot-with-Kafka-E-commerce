package com.food.ordering.system.springwork3.payment.model.repository;

import com.food.ordering.system.springwork3.payment.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

