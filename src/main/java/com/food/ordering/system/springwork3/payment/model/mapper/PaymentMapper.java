package com.food.ordering.system.springwork3.payment.model.mapper;

import com.food.ordering.system.springwork3.order.model.entity.Order;
import com.food.ordering.system.springwork3.payment.model.entity.Payment;
import com.food.ordering.system.springwork3.payment.model.dto.PaymentDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentMapper {

    public static PaymentDTO toDTO(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .paymentMethod(payment.getPaymentMethod())
                .paymentDate(payment.getPaymentDate())
                .amount(payment.getAmount())
                .orderId(payment.getOrder().getId())
                .deleted(payment.isDeleted())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    public static Payment toEntity(PaymentDTO paymentDTO) {
        return Payment.builder()
                .id(paymentDTO.getId())
                .paymentMethod(paymentDTO.getPaymentMethod())
                .paymentDate(paymentDTO.getPaymentDate())
                .amount(paymentDTO.getAmount())
                .order(Order.builder().id(paymentDTO.getOrderId()).build())
                .deleted(paymentDTO.isDeleted())
                .createdAt(paymentDTO.getCreatedAt())
                .build();
    }

    public static void partialUpdate(PaymentDTO paymentDTO, Payment payment) {
        if (paymentDTO.getPaymentMethod() != null) {
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        }
        if (paymentDTO.getPaymentDate() != null) {
            payment.setPaymentDate(paymentDTO.getPaymentDate());
        }
        if (paymentDTO.getAmount() != 0) {
            payment.setAmount(paymentDTO.getAmount());
        }
        if (paymentDTO.getOrderId() != null) {
            payment.setOrder(Order.builder().id(paymentDTO.getOrderId()).build());
        }
    }


    public static List<PaymentDTO> toDTOList(List<Payment> payments) {
        return payments.stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Payment> toEntityList(List<PaymentDTO> paymentDTOs) {
        return paymentDTOs.stream()
                .map(PaymentMapper::toEntity)
                .collect(Collectors.toList());
    }
}
