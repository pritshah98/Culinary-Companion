package com.ps.culinarycompanion.dao;

import com.ps.culinarycompanion.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);
}
