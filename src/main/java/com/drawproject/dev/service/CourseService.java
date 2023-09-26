package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.CourseDTO;
import com.drawproject.dev.dto.course.CoursePreviewDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapModel;
import com.drawproject.dev.model.Category;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.model.Skill;
import com.drawproject.dev.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    /**
     * Gets top course by category.
     *
     * @return top list course preview DTO by category
     */
    public ResponseDTO getTopCourseByCategory(int limit) {
        Map<Integer, List<CoursePreviewDTO>> list = new HashMap<>();

        categoryRepository.findAll().forEach(category ->
                list.put(category.getCategoryId(), MapModel.mapListToDTO(
                        courseRepository.findTopCourseByCategory
                                (category.getCategoryId(), limit))));

        return new ResponseDTO(HttpStatus.OK, "Request Successfully!", list);
    }

    public ResponsePagingDTO searchCourse(int page, int eachPage, Integer star,
                                    List<Integer> categories, List<Integer> skills, String search) {

        Pageable pageable = PageRequest.of(page - 1, eachPage);


        if (categories == null) {
            categories = categoryRepository.findAll().stream().map(Category::getCategoryId).toList();
        }
        if (skills == null) {
            skills = skillRepository.findAll().stream().map(Skill::getSkillId).toList();
        }
        Page<Courses> courses = courseRepository.searchCourse(categories, skills, search, star, pageable);
        int totalPage = courses.getTotalPages();

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.NOT_FOUND, "Course not found",
                courses.getTotalElements(), page, courses.getTotalPages(), eachPage, MapModel.mapListCourseDetailsToDTO(courses));

        if(!courses.isEmpty()) {
            responsePagingDTO.setMessage("Course found");
            responsePagingDTO.setStatus(HttpStatus.OK);
        }

        return responsePagingDTO;
    }

    public ResponseDTO saveCourse(CourseDTO courseDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        Courses course;

        if (courseDTO.getCourseId() == 0) {
            course = new Courses();
            responseDTO.setMessage("Create new Courses Successfully");
        } else {
            course = courseRepository.findById(courseDTO.getCourseId()).orElseThrow();
            responseDTO.setMessage("Update Course Successfully");
        }
        modelMapper.map(courseDTO, course);

        try {
            course.setImage(courseDTO.getImage().getBytes());
            responseDTO.setStatus(HttpStatus.OK);
            responseDTO.setData(courseRepository.save(course));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return responseDTO;
    }

    public ResponseDTO getCourseDetailsById(int courseId) {

        Courses course = courseRepository.findById(courseId).orElseThrow();

        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, "FOUND COURSE", MapModel.mapCourseDetailsToDTO(course));

        return responseDTO;
    }

    public ResponseDTO deleteCourse(int courseId) {

        if(!courseRepository.existsById(courseId)) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Course not found", null);
        }

        courseRepository.deleteById(courseId);

        return new ResponseDTO(HttpStatus.OK, "Delete course successfully", "TRUE");
    }

    public Object getCoursesByUser(int userId, int page, int eachPage) {

        if(!userRepository.existsById(userId)) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "User not found", null);
        }

        Pageable pageable = PageRequest.of(page - 1, eachPage);

        Page<Courses> course = courseRepository.findByUsersUserId(userId, pageable);

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.NOT_FOUND, "Course not found",
                course.getTotalElements(), page, course.getTotalPages(), eachPage, MapModel.mapListToDTO(course.getContent()));

        if(!course.isEmpty()) {
            responsePagingDTO.setMessage("Course found");
            responsePagingDTO.setStatus(HttpStatus.OK);
        }

        return responsePagingDTO;
    }

}
