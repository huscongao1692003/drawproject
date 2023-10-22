package com.drawproject.dev.repository;

import com.drawproject.dev.model.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Integer> {
    Boolean existsByUserUserIdAndCourseCourseId(int userId, int courseId);
    int countByCourseCourseIdAndStatus(int courseId, String status);

    Optional<Enroll> findByUserUserIdAndCourseCourseId(int userId, int courseId);
}