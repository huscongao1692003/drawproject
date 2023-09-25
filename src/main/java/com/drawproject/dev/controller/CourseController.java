package com.drawproject.dev.controller;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.CourseDTO;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/top-courses")
    public ResponseEntity<ResponseDTO> getTopCourse(@RequestParam(value = "limit", required = false, defaultValue = "3") int limit) {
        return ResponseEntity.ok().body(courseService.getTopCourseByCategory(limit));
    }

    @GetMapping("/courses/search")
    public ResponseEntity<ResponseDTO> searchCourse(@RequestParam("page") int page, @RequestParam("eachPage") int eachPage,
                                                    @RequestParam(value = "category", required = false) List<Integer> categories,
                                                    @RequestParam(value = "skill", required = false) List<Integer> skills,
                                                    @RequestParam(value = "star", required = false, defaultValue = "0") int star,
                                                    @RequestParam(value = "search", required = false, defaultValue = "") String search) {

        return ResponseEntity.ok().body(courseService.searchCourse(page, eachPage, star, categories, skills, search));
    }

    @PostMapping(value = "/courses", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDTO> createUpdate(@Valid CourseDTO courseDTO) {
        return ResponseEntity.ok().body(courseService.saveCourse(courseDTO));
    }


}
