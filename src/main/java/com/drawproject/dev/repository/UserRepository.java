package com.drawproject.dev.repository;

import com.drawproject.dev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User readByEmail(String email);

    User findByUserId(int userId);

    List<User> findByRolesName(String role);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}
