package com.food.ordering.system.springwork3.cartItem.model.entity;

import com.food.ordering.system.springwork3.base.model.BaseEntity;
import com.food.ordering.system.springwork3.product.model.entity.Product;
import com.food.ordering.system.springwork3.user.model.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "product_id"})
})
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class CartItem extends BaseEntity {

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
