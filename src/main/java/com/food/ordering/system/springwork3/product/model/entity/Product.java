package com.food.ordering.system.springwork3.product.model.entity;

import com.food.ordering.system.springwork3.base.model.BaseEntity;
import com.food.ordering.system.springwork3.cartItem.model.entity.CartItem;
import com.food.ordering.system.springwork3.order.model.entity.Order;
import com.food.ordering.system.springwork3.product.model.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
@Table(name = "products")
public class Product extends BaseEntity {

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @PositiveOrZero(message = "Stock quantity cannot be less than zero")
    private int stockQuantity;

    @Min(value = 0, message = "Price cannot be negative")
    private double price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;
}
