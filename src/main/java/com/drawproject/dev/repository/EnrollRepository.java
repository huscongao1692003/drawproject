package com.drawproject.dev.repository;

import com.drawproject.dev.model.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollRepository extends JpaRepository<Enroll, Integer> {
    Boolean existsByUserUserIdAndCourseCourseId(int userId, int courseId);
    int countByCourseCourseIdAndStatus(int courseId, String status);
}