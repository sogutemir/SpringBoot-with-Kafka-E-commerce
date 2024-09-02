package com.food.ordering.system.springwork3.payment.model.entity;

import com.food.ordering.system.springwork3.base.model.BaseEntity;
import com.food.ordering.system.springwork3.order.model.entity.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class Payment extends BaseEntity {

    @NotBlank(message = "Payment method cannot be blank")
    private String paymentMethod;

    private LocalDateTime paymentDate;

    @Min(value = 0, message = "Amount cannot be negative")
    private double amount;

    @OneToOne(mappedBy = "payment", fetch = FetchType.EAGER)
    private Order order;
}
