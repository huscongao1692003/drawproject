package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.LessonDTO;
import com.drawproject.dev.dto.LessonRequestDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.map.MapLesson;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.model.Lesson;
import com.drawproject.dev.model.Topic;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.repository.LessonRepository;
import com.drawproject.dev.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LessonService {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FileService fileService;

    @Autowired
    CourseRepository courseRepository;

    /*
    @Transactional
    public ResponseDTO createLessons(List<MultipartFile> listFile, Topic topic, List<LessonDTO> lessonDTOs) {
        List<Lesson> lessonErrors = new ArrayList<>();
        try {

            MapLesson.mapDTOtoLessons(lessonDTOs).forEach(lesson -> {
                try {
                        if (lesson.getTypeFile().equalsIgnoreCase(DrawProjectConstaints.VIDEO)) {
                            createLesson(topic, lesson);
                        } else {
                            MultipartFile file = listFile.stream().filter(f -> f.getOriginalFilename().equalsIgnoreCase(lesson.getUrl())).findFirst().orElseThrow();
                            createLesson(file, lesson.getTypeFile(), topic, lesson);
                        }
                } catch (Exception e) {
                    lessonErrors.add(lesson);
                }
            });

        } catch (Exception e) {
            topicRepository.delete(topic);
        }
        if(lessonErrors.isEmpty()) {
            return new ResponseDTO(HttpStatus.OK, "Lesson created", lessonErrors);
        }
        return new ResponseDTO(HttpStatus.BAD_REQUEST, "Existed error", lessonErrors);

    }

    @Transactional
    public void createLesson(Topic topic, Lesson lesson) {
        Lesson newLesson = new Lesson();
        modelMapper.map(lesson, newLesson);
        newLesson.setTopic(topic);
        newLesson.setStatus(DrawProjectConstaints.OPEN);
        lessonRepository.save(newLesson);
    }

    @Transactional
    public void createLesson(MultipartFile file, String typeFile, Topic topic, Lesson lesson) {
        Lesson newLesson = new Lesson();
        modelMapper.map(lesson, newLesson);
        newLesson.setTopic(topic);
        newLesson.setStatus(DrawProjectConstaints.OPEN);
        newLesson.setUrl(fileService.uploadFile(file, lesson.getLessonId(), typeFile, "lessons"));
        lessonRepository.save(newLesson);
    }

    */


    public String getTrailler(int courseId) {
        Lesson lesson= lessonRepository.findByNumberAndTopicNumberAndTopicCourseCourseIdAndStatus(1, 1, courseId, DrawProjectConstaints.OPEN);
        return lesson.getUrl();
    }

    @Transactional
    public void deleteLessons(int topicId) {
        List<Lesson> lessons = lessonRepository.findByTopicTopicIdAndStatus(topicId, DrawProjectConstaints.OPEN);
        if(lessons.isEmpty()) {
            return;
        }
        lessons.forEach(lesson -> {
            lesson.setStatus(DrawProjectConstaints.CLOSE);
            lessonRepository.save(lesson);
        });
        checkLesson(lessons.get(0).getTopic().getCourse().getCourseId());
    }

    @Transactional
    public ResponseDTO deleteLesson(int lessonId) {
        Lesson lesson= lessonRepository.findById(lessonId).orElseThrow();
        lesson.setStatus(DrawProjectConstaints.CLOSE);
        lessonRepository.save(lesson);
        checkIndexLesson(lesson.getTopic().getTopicId());
        checkLesson(lesson.getTopic().getCourse().getCourseId());
        return new ResponseDTO(HttpStatus.OK, "Delete lesson successfully", "");
    }

    @Transactional
    public void checkLesson(int courseId) {
        if(lessonRepository.countByTopicCourseCourseIdAndStatus(courseId, DrawProjectConstaints.OPEN) <= 3) {
            Courses course = courseRepository.findById(courseId).orElseThrow();

            course.setStatus(DrawProjectConstaints.CLOSE);

            courseRepository.save(course);
        }
    }

    public ResponseDTO createLesson(MultipartFile file, LessonRequestDTO lessonRequestDTO) {
        Lesson lesson = new Lesson();
        modelMapper.map(lessonRequestDTO, lesson);
        lesson.setStatus(DrawProjectConstaints.OPEN);
        Optional<Topic> topic = topicRepository.findById(lessonRequestDTO.getTopicId());
        if(topic.isEmpty()) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Topic not found", "Error when creating with your topic");
        }
        lesson.setTopic(topic.get());
        if(!lessonRequestDTO.getTypeFile().equalsIgnoreCase(DrawProjectConstaints.VIDEO)) {
            lesson = lessonRepository.save(lesson);
            lesson.setUrl(fileService.uploadFile(file, lesson.getLessonId(), lessonRequestDTO.getTypeFile(), "lessons"));
        }
        lessonRepository.save(lesson);
        return new ResponseDTO(HttpStatus.CREATED, "Lesson created successfully", "Save lesson successfully");
    }

    public ResponseDTO updateLesson(MultipartFile file, LessonDTO lessonDTO, int topicId) {
        Optional<Lesson> lesson = lessonRepository.findById(lessonDTO.getLessonId());
        if(lesson.isEmpty()) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Lesson not found", "Error while updating lesson");
        }
        Lesson newLesson = lesson.get();
        modelMapper.map(lessonDTO, newLesson);

        Optional<Topic> topic = topicRepository.findById(topicId);
        if(topic.isEmpty()) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Topic not found", "Error while getting topic");
        }
        newLesson.setTopic(topic.get());

        if((!lessonDTO.getTypeFile().equalsIgnoreCase(DrawProjectConstaints.VIDEO)) && (!newLesson.getUrl().equalsIgnoreCase(lessonDTO.getUrl()))) {
            lesson.get().setUrl(fileService.uploadFile(file, lesson.get().getLessonId(), lessonDTO.getTypeFile(), "lessons"));
        }

        lessonRepository.save(newLesson);
        return new ResponseDTO(HttpStatus.OK, "Update lesson successfully", "Save lesson successfully");
    }

    public void checkIndexLesson(int topicId) {
        List<Lesson> lessons = lessonRepository.findByTopicTopicIdAndStatusOrderByNumber(topicId, DrawProjectConstaints.OPEN);
        for(int i = 0; i < lessons.size(); i++) {
            if(lessons.get(i).getNumber() != (i + 1)) {
                lessons.get(i).setNumber(i + 1);
                lessonRepository.save(lessons.get(i));
            }
        }
    }

    /*
    @Transactional
    public ResponseDTO updateLessons(List<MultipartFile> listFile, Topic topic, List<LessonDTO> lessonDTOs) {
        List<Lesson> lessonErrors = new ArrayList<>();
        try {

            MapLesson.mapDTOtoLessons(lessonDTOs).forEach(lesson -> {
                try {
                    if (lesson.getTypeFile().equalsIgnoreCase(DrawProjectConstaints.VIDEO)) {
                        updateLesson(topic, lesson);
                    } else {
                        MultipartFile file = listFile.stream().filter(f -> f.getOriginalFilename().equalsIgnoreCase(lesson.getUrl())).findFirst().orElseThrow();
                        updateLesson(file, lesson.getTypeFile(), topic, lesson);
                    }
                } catch (Exception e) {
                    lessonErrors.add(lesson);
                }
            });

        } catch (Exception e) {
            topicRepository.delete(topic);
        }
        deleteLesson(topic.getTopicId(), lessonDTOs.stream().map(LessonDTO::getLessonId).toList());
        if(lessonErrors.isEmpty()) {
            return new ResponseDTO(HttpStatus.OK, "Lesson updated", lessonErrors);
        }


        return new ResponseDTO(HttpStatus.BAD_REQUEST, "Existed error", lessonErrors);

    }

    @Transactional
    public void updateLesson(Topic topic, Lesson lesson) {
        Lesson newLesson = lessonRepository.findById(lesson.getLessonId()).orElseThrow();
        modelMapper.map(lesson, newLesson);
        newLesson.setTopic(topic);
        newLesson.setStatus(DrawProjectConstaints.OPEN);
        lessonRepository.save(newLesson);
    }

    @Transactional
    public void updateLesson(MultipartFile file, String typeFile, Topic topic, Lesson lesson) {
        Lesson newLesson = lessonRepository.findById(lesson.getLessonId()).orElseThrow();
        modelMapper.map(lesson, newLesson);
        newLesson.setTopic(topic);
        newLesson.setStatus(DrawProjectConstaints.OPEN);
        newLesson.setUrl(fileService.uploadFile(file, lesson.getLessonId(), typeFile, "lessons"));
        lessonRepository.save(newLesson);

    }
*/



}
