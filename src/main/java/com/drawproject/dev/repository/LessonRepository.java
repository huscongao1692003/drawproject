package com.drawproject.dev.repository;

import com.drawproject.dev.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByTopicTopicId(int topicId);

    int countByTopicCourseCourseId(int courseId);

    Lesson findByNumberAndTopicNumberAndTopicCourseCourseIdAndStatus(int indexLesson, int indexTopic, int courseId, String status);
}
