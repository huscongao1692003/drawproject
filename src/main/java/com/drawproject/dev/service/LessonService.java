package com.drawproject.dev.service;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.model.Lesson;
import com.drawproject.dev.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    @Autowired
    LessonRepository lessonRepository;

    public ResponseDTO getLessonByTopic(int topicId) {
        List<Lesson> lessons = lessonRepository.findByTopicTopicId(topicId);
        if(lessons.isEmpty()) {
            return new ResponseDTO(HttpStatus.NO_CONTENT, "No lessons found", lessons);
        }
        return new ResponseDTO(HttpStatus.OK, "Lesson found", lessons);
    }

}
