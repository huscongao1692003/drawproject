package com.drawproject.dev.service;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.TopicDTO;
import com.drawproject.dev.map.MapTopic;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.model.Topic;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    LessonService lessonService;

    public ResponseDTO getTopicByCourse(int courseId) {
        if(!courseRepository.existsById(courseId)) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Course not found", null);
        }
        List<Topic> topics = topicRepository.findByCourseCourseId(courseId);
        if(topics.isEmpty()) {
            return new ResponseDTO(HttpStatus.NO_CONTENT, "Topic not found", MapTopic.mapTopicpsToDTOs(topics));
        }
        return new ResponseDTO(HttpStatus.OK, "Topic found", MapTopic.mapTopicpsToDTOs(topics));
    }

    public ResponseDTO createTopic(int courseId, TopicDTO topicDTO) {

        Courses course = courseRepository.findById(courseId).orElseThrow();

        Topic topic = new Topic();
        topic.setTopicTitle(topicDTO.getTopicTitle());
        topic.setCourse(course);
        //save lesson
        lessonService.createLessons(topicRepository.save(topic), topicDTO.getLessons());

        return new ResponseDTO(HttpStatus.CREATED, "Topic created", "ok");
    }

}
