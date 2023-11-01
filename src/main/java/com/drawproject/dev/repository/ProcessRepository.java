package com.drawproject.dev.repository;

import com.drawproject.dev.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer> {
    long countByEnroll_EnrollIdAndLesson_Topic_Course_CourseIdAndLesson_Status(int enrollId, int courseId, String status);
    int countByEnrollUserUserIdAndEnrollCourseCourseId(int userId, int courseId);
    Process findTopByEnrollUserUserIdAndEnrollCourseCourseIdOrderByLessonNumber(int userId, int courseId);
    Process findTopByEnrollUserUserIdAndEnrollCourseCourseIdOrderByLessonNumberDesc(int userId, int courseId);

    Process findByEnrollEnrollIdAndLessonLessonId(int enrollId, int lessonId);
}