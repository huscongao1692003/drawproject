package com.drawproject.dev.repository;

import com.drawproject.dev.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Feedback.
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    boolean existsByUser_UserIdAndCourses_CourseIdAndStatus(int userId, int courseId, String status);
    Page<Feedback> findByCoursesCourseIdAndStatus(int id, String status, Pageable pageable);
    Page<Feedback> findByCoursesCourseId(int id, Pageable pageable);
}
