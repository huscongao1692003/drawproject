package com.drawproject.dev.service;

import com.drawproject.dev.dto.CoursePreviewDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.map.MapModel;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.repository.CategoryRepository;
import com.drawproject.dev.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Course service.
 */
@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Gets top course by category.
     *
     * @return top list course preview DTO by category
     */
    public List<List<CoursePreviewDTO>> getTopCourseByCategory() {
        List<List<CoursePreviewDTO>> coursesPreview = new ArrayList<>();

        categoryRepository.findAll().forEach(category -> {
            coursesPreview.add(MapModel.mapListToDTO(
                    courseRepository.findTopCourseByCategory(category.getCategoryId())));
        });

        return coursesPreview;
    }

    public ResponseDTO getCourseByCategory(int page, int eachPage) {

        Pageable pageable = PageRequest.of(page, eachPage);
        List<Courses> courses = courseRepository.findAll(pageable).getContent();

        int totalPage = (int) Math.ceil((double) courseRepository.count() / eachPage);

        return new ResponseDTO(page, totalPage, eachPage, MapModel.mapListToDTO(courses));
    }
}
