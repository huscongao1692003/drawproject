package com.drawproject.dev.repository;

import com.drawproject.dev.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    int countByLessonTopicCourseCourseId(int courseId);

    List<Assignment> findByLessonLessonIdAndStatusIs(int lessonId, String status);

}