package com.drawproject.dev.repository;

import com.drawproject.dev.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByTopicTopicIdAndStatus(int topicId, String status);

    int countByTopicCourseCourseIdAndStatus(int courseId, String status);

    Lesson findByNumberAndTopicNumberAndTopicCourseCourseIdAndStatus(int indexLesson, int indexTopic, int courseId, String status);

    Lesson findByLessonId(int lessonId);
}
