package com.food.ordering.system.springwork3.payment.model.entity;

import com.food.ordering.system.springwork3.base.model.BaseEntity;
import com.food.ordering.system.springwork3.order.model.entity.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;

import java.math.BigDecimal;
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

    @PositiveOrZero(message = "Amount cannot be negative")
    private BigDecimal amount = BigDecimal.ZERO;

    @OneToOne(mappedBy = "payment", fetch = FetchType.EAGER)
    private Order order;
}
