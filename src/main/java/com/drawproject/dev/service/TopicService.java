package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.TopicDTO;
import com.drawproject.dev.map.MapTopic;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.model.Topic;
import com.drawproject.dev.repository.AssignmentRepository;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    LessonService lessonService;

    @Autowired
    AssignmentService assignmentService;


    public ResponseDTO getTopicByCourse(int courseId) {
        if(!courseRepository.existsById(courseId)) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Course not found", null);
        }
        List<Topic> topics = topicRepository.findByCourseCourseIdAndStatusOrderByNumber(courseId, DrawProjectConstaints.OPEN);
        if(topics.isEmpty()) {
            return new ResponseDTO(HttpStatus.NO_CONTENT, "Topic not found", MapTopic.mapTopicpsToDTOs(topics));
        }

        List<TopicDTO> topicsDTOs = MapTopic.mapTopicpsToDTOs(topics);
//        topicsDTOs.forEach(topicDTO -> {
//            topicDTO.getLessons().forEach(lessonDTO -> {
//                lessonDTO.setListAssignment(assignmentService.getAssignmentDTOs(lessonDTO.getLessonId()));
//            });
//        });

        return new ResponseDTO(HttpStatus.OK, "Topic found", topicsDTOs);
    }

//    @Transactional
//    public ResponseDTO createTopic(List<MultipartFile> listFile, int courseId, TopicDTO topicDTO) {
//
//        Courses course = courseRepository.findById(courseId).orElseThrow();
//
//        Topic topic = new Topic();
//        topic.setTopicTitle(topicDTO.getTopicTitle());
//        topic.setCourse(course);
//        topic.setNumber(topicDTO.getNumber());
//        topic.setStatus(DrawProjectConstaints.OPEN);
//        //save lesson
//        ResponseDTO responseDTO = lessonService.createLessons(listFile, topicRepository.save(topic), topicDTO.getLessons());
//        if(responseDTO.getStatus().isError()) {
//            return new ResponseDTO(HttpStatus.CREATED, "Topic created! Some lessons is error", responseDTO.getData());
//        }
//
//        return new ResponseDTO(HttpStatus.CREATED, "Topic created", "ok");
//    }

    public ResponseDTO saveTopic(TopicDTO topicDTO, int courseId) {
        Topic topic;
        if(topicDTO.getTopicId() == 0) {
            topic = new Topic();
        } else {
            topic = topicRepository.findById(topicDTO.getTopicId()).orElseThrow();
        }
        if(topicRepository.existsByCourse_CourseIdAndTopicTitleIgnoreCaseAndStatus(courseId, topicDTO.getTopicTitle(), DrawProjectConstaints.OPEN)) {
            return new ResponseDTO(HttpStatus.NOT_MODIFIED, "CourseTitle have been existed", "");
        }
        Optional<Courses> course = courseRepository.findById(courseId);
        if(course.isEmpty()) {
            return new ResponseDTO(HttpStatus.NOT_MODIFIED, "Course has not been found", "");
        }
        topic.setCourse(course.get());
        topic.setTopicTitle(topicDTO.getTopicTitle());
        topic.setStatus(DrawProjectConstaints.OPEN);
        topic.setNumber(topicDTO.getNumber());
        topicRepository.save(topic);
        return new ResponseDTO(HttpStatus.CREATED, "Topic has been saved", "Save topic successfully!");
    }

    public ResponseDTO deleteTopic(int topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        topic.setStatus(DrawProjectConstaints.CLOSE);
        lessonService.deleteLesson(topicId);
        topicRepository.save(topic);
        return new ResponseDTO(HttpStatus.OK, "Topic deleted", "Topic and lessons deleted");
    }

//    public ResponseDTO updateTopic(List<MultipartFile> listFile, int courseId, TopicDTO topicDTO) {
//        Topic topic = topicRepository.findById(topicDTO.getTopicId()).orElseThrow();
//        topic.setTopicTitle(topicDTO.getTopicTitle());
//        topic.setNumber(topicDTO.getNumber());
//        topic.setStatus(DrawProjectConstaints.OPEN);
//        //save lesson
//        ResponseDTO responseDTO = lessonService.updateLessons(listFile, topicRepository.save(topic), topicDTO.getLessons());
//        if(responseDTO.getStatus().isError()) {
//            return new ResponseDTO(HttpStatus.CREATED, "Topic updated! Some lessons is error", responseDTO.getData());
//        }
//
//        return new ResponseDTO(HttpStatus.CREATED, "Topic updated", "ok");
//    }


}
