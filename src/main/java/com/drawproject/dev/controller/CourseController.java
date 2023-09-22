package com.drawproject.dev.controller;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.CoursePreviewDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.service.CategoryService;
import com.drawproject.dev.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<ResponseDTO> getTopCourse(@RequestParam(value = "limit", required = false, defaultValue = "3") int limit) {
        ResponseDTO rep = new ResponseDTO(HttpStatus.OK, "Request successfully", courseService.getTopCourseByCategory(limit));
        return ResponseEntity.ok().body(rep);
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

        ResponseDTO rep = new ResponseDTO(HttpStatus.OK, "Request successfully", courseService.getCourseByCategory(page, eachPage));
        return ResponseEntity.ok().body(rep);
    }

    @GetMapping("/courses/search")
    public ResponseEntity<ResponseDTO> searchCourse(@RequestParam("page") int page, @RequestParam("eachPage") int eachPage,
                                                    @RequestParam(value = "category", required = false) List<Integer> categories,
                                                    @RequestParam(value = "skill", required = false) List<Integer> skills,
                                                    @RequestParam(value = "star", required = false) Integer star,
                                                    @RequestParam(value = "search", required = false, defaultValue = "") String search) {

        ResponseDTO rep = new ResponseDTO(HttpStatus.OK, "Request successfully", courseService.searchCourse(page, eachPage, star, categories, skills, search));
        return ResponseEntity.ok().body(rep);
    }

}
