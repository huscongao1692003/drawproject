package com.drawproject.dev.service;

import com.drawproject.dev.dto.course.CoursePreviewDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapModel;
import com.drawproject.dev.model.Category;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.model.Skill;
import com.drawproject.dev.repository.CategoryRepository;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.repository.SkillRepository;
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

    @Autowired
    SkillRepository skillRepository;

    /**
     * Gets top course by category.
     *
     * @return top list course preview DTO by category
     */
    public List<List<CoursePreviewDTO>> getTopCourseByCategory(int limit) {
        List<List<CoursePreviewDTO>> coursesPreview = new ArrayList<>();

        categoryRepository.findAll().forEach(category -> {
            coursesPreview.add(MapModel.mapListToDTO(
                    courseRepository.findTopCourseByCategory(category.getCategoryId(), limit)));
        });

        return coursesPreview;
    }

    /**
     * Gets course by category.
     *
     * @param page     the page
     * @param eachPage the each page
     *
     * @return the course by category
     */
    public ResponsePagingDTO getCourseByCategory(int page, int eachPage) {

        Pageable pageable = PageRequest.of(page, eachPage);
        List<Courses> courses = courseRepository.findAll(pageable).getContent();

        int totalPage = (int) Math.ceil((double) courseRepository.count() / eachPage);

        return new ResponsePagingDTO(page, totalPage, eachPage, MapModel.mapListToDTO(courses));
    }

    public ResponsePagingDTO searchCourse(int page, int eachPage, int star,
                                          List<Integer> categories, List<Integer> skills, String search) {

        Pageable pageable = PageRequest.of(page, eachPage);

        if(categories == null) {
            categories = categoryRepository.findAll().stream().map(Category::getCategoryId).toList();
        }

        if(skills == null) {
            skills = skillRepository.findAll().stream().map(Skill::getSkillId).toList();
        }

        List<Courses> courses = courseRepository.searchCourse(categories, skills, search, star, pageable);


        int totalPage = (int) Math.ceil((double) courseRepository.countSearchCourse(categories, skills, star, search) / eachPage);

        return new ResponsePagingDTO(page, totalPage, eachPage, MapModel.mapListToDTO(courses));
    }

}
