package com.food.ordering.system.springwork3.user.model.repository;

import com.food.ordering.system.springwork3.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
