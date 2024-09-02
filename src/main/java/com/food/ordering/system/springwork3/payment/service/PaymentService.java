package com.food.ordering.system.springwork3.payment.service;

import com.food.ordering.system.springwork3.payment.model.dto.PaymentDTO;

public interface PaymentService {

    PaymentDTO processPayment(PaymentDTO paymentDTO);

    void handleOrderMessage(String orderMessage);
}
