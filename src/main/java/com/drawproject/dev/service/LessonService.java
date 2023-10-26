package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.LessonDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.map.MapLesson;
import com.drawproject.dev.model.Lesson;
import com.drawproject.dev.model.Topic;
import com.drawproject.dev.repository.LessonRepository;
import com.drawproject.dev.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    @Autowired
    FileService fileService;

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
        lessonRepository.save(newLesson);
    }

    @Transactional
    public void createLesson(MultipartFile file, String typeFile, Topic topic, Lesson lesson) {
        Lesson newLesson = new Lesson();
        modelMapper.map(lesson, newLesson);
        newLesson.setTopic(topic);
        newLesson.setStatus(DrawProjectConstaints.OPEN);
        newLesson.setUrl(fileService.uploadFile(file, 1, typeFile, "lessons"));
        lessonRepository.save(newLesson);
    }

    public String getTrailler(int courseId) {
        Lesson lesson= lessonRepository.findByNumberAndTopicNumberAndTopicCourseCourseIdAndStatus(1, 1, courseId, DrawProjectConstaints.OPEN);
        return lesson.getUrl();
    }
}
