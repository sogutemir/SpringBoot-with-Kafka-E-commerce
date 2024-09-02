package com.food.ordering.system.springwork3.base.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    private LocalDateTime createdAt;

    @PrePersist
    public void init() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}