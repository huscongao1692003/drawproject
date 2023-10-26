package com.drawproject.dev.repository;

import com.drawproject.dev.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer> {
    int countByEnrollUserUserIdAndEnrollCourseCourseId(int userId, int courseId);
    Process findTopByEnrollUserUserIdAndEnrollCourseCourseIdOrderByLessonNumber(int userId, int courseId);
    Process findTopByEnrollUserUserIdAndEnrollCourseCourseIdOrderByLessonNumberDesc(int userId, int courseId);
}