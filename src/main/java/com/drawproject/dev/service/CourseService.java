package com.drawproject.dev.service;

import com.drawproject.dev.dto.CoursePreviewDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.map.MapModel;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.repository.CategoryRepository;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.repository.LessonRepository;
import com.drawproject.dev.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    TopicRepository topicRepository;

    public List<List<CoursePreviewDTO>> getTopCourseByCategory() {
        List<List<CoursePreviewDTO>> coursesPreview = new ArrayList<>();

        categoryRepository.findAll().forEach(category -> {
            coursesPreview.add(MapModel.mapListToDTO(
                    courseRepository.findTopCourseByCategory(category.getCategoryId())));
        });

        return coursesPreview;
    }
}
