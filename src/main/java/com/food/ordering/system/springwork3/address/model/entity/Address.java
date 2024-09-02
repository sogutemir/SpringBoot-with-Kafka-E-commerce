package com.food.ordering.system.springwork3.address.model.entity;

import com.food.ordering.system.springwork3.base.model.BaseEntity;
import com.food.ordering.system.springwork3.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class Address extends BaseEntity {

    private String street;
    private String city;
    private String state;
    private String zipCode;

    @OneToOne(mappedBy = "address", fetch = FetchType.EAGER)
    private User user;
}