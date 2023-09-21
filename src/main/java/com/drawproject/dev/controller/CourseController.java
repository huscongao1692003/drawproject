package com.drawproject.dev.controller;

import com.drawproject.dev.dto.CoursePreviewDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.service.CategoryService;
import com.drawproject.dev.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    CategoryService categoryService;

    /**
     * Gets top course.
     *
     * @return the top list course by list category
     */
    @GetMapping("/topcourses")
    public ResponseEntity<List<List<CoursePreviewDTO>>> getTopCourse(@RequestParam(value = "limit", required = false, defaultValue = "3") int limit) {
        return ResponseEntity.ok().body(courseService.getTopCourseByCategory(limit));
    }


    /**
     * Gets courses.
     *
     * @param page     : the page to show
     * @param eachPage : the each page have limit course showing on the page
     * @return the courses
     */
    @GetMapping("/courses")
    public ResponseEntity<ResponseDTO> getCourses(@RequestParam("page") int page,
                                                            @RequestParam("eachPage") int eachPage) {

        return ResponseEntity.ok().body(courseService.getCourseByCategory(page, eachPage));
    }



}
