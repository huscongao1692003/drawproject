package com.drawproject.dev.repository;

import com.drawproject.dev.model.Roles;
import com.drawproject.dev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByName(String name);
}
