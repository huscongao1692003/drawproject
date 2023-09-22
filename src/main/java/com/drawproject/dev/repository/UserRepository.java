package com.drawproject.dev.repository;

import com.drawproject.dev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User readByEmail(String email);

    User findUserByUserId(int userId);

    List<User> findByRolesName(String role);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}
