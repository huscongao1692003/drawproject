package com.drawproject.dev.service;

import com.drawproject.dev.dto.LessonDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.map.MapLesson;
import com.drawproject.dev.model.Lesson;
import com.drawproject.dev.model.Topic;
import com.drawproject.dev.repository.LessonRepository;
import com.drawproject.dev.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LessonService {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    ModelMapper modelMapper;

    public ResponseDTO getLessonByTopic(int topicId) {
        List<Lesson> lessons = lessonRepository.findByTopicTopicId(topicId);
        if(lessons.isEmpty()) {
            return new ResponseDTO(HttpStatus.NO_CONTENT, "No lessons found", lessons);
        }
        return new ResponseDTO(HttpStatus.OK, "Lesson found", lessons);
    }

    public ResponseDTO createLessons(Topic topic, List<LessonDTO> lessonDTOs) {
        MapLesson.mapDTOtoLessons(lessonDTOs).forEach(lesson -> {
            createLesson(topic, lesson);
        });

        return new ResponseDTO(HttpStatus.OK, "Lesson created", lessonDTOs);

    }

    public void createLesson(Topic topic, Lesson lesson) {
        Lesson newLesson = new Lesson();
        modelMapper.map(lesson, newLesson);
        newLesson.setTopic(topic);
        lessonRepository.save(newLesson);
    }

}
