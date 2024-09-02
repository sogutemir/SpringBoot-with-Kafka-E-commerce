package com.food.ordering.system.springwork3.product.model.entity;

import com.food.ordering.system.springwork3.base.model.BaseEntity;
import com.food.ordering.system.springwork3.cartItem.model.entity.CartItem;
import com.food.ordering.system.springwork3.order.model.entity.OrderProduct;
import com.food.ordering.system.springwork3.product.model.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;

import java.math.BigDecimal;
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

    @PositiveOrZero(message = "Price cannot be negative")
    private BigDecimal price = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;
}
