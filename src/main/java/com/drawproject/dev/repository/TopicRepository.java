package com.drawproject.dev.repository;

import com.drawproject.dev.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    List<Topic> findByCourseCourseIdAndStatus(int courseId, String status);

    //   Student findBy
}
