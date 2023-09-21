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
    @GetMapping("/")
    public ResponseEntity<List<List<CoursePreviewDTO>>> getTopCourse() {
        return ResponseEntity.ok().body(courseService.getTopCourseByCategory());
    }


    @GetMapping("/courses")
    public ResponseEntity<ResponseDTO> getCourses(@RequestParam("page") int page,
                                                            @RequestParam("eachPage") int eachPage) {

        return ResponseEntity.ok().body(courseService.getCourseByCategory(page, eachPage));
    }

}
