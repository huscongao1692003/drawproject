package com.drawproject.dev.repository;

import com.drawproject.dev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User readByEmail(String email);

    User findByUserId(int userId);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}
