package com.drawproject.dev.repository;

import com.drawproject.dev.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUserIdInAndStatus(List<Integer> userIds, String status);
    Page<User> findByStatusAndRoles_Name(String status, String name, Pageable pageable);

    User readByEmail(String email);

    User findUserByUserId(int userId);

    List<User> findByRolesName(String role);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Page<User> findByEnrollsCourseCourseId(int id, Pageable pageable);

}
