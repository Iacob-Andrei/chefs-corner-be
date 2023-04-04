package com.chefscorner.auth.repository;

import com.chefscorner.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
