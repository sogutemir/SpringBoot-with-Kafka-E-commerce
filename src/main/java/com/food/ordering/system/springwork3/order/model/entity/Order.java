package com.food.ordering.system.springwork3.order.model.entity;

import com.food.ordering.system.springwork3.base.model.BaseEntity;
import com.food.ordering.system.springwork3.order.model.OrderStatus;
import com.food.ordering.system.springwork3.payment.model.entity.Payment;
import com.food.ordering.system.springwork3.user.model.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
@Table(name = "orders")
public class Order extends BaseEntity {

    private LocalDateTime orderDate;

    @NotNull(message = "Order status cannot be null")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id", referencedColumnName = "id", nullable = true)
    private Payment payment;

}