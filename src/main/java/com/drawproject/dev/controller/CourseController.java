package com.drawproject.dev.controller;

import com.drawproject.dev.dto.report.ReportStudentDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.CourseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.service.CourseService;
import com.drawproject.dev.service.ReportStudentService;
import com.drawproject.dev.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    ReportStudentService reportStudentService;

    @GetMapping("/top-courses")
    public ResponseEntity<ResponseDTO> getTopCourse(@RequestParam(value = "limit", required = false, defaultValue = "3") int limit) {
        return ResponseEntity.ok().body(courseService.getTopCourseByCategory(limit));
    }

    @GetMapping("")
    public ResponseEntity<ResponsePagingDTO> searchCourse(@RequestParam(value = "page", defaultValue = "1") int page,
                                                          @RequestParam(value = "eachPage", defaultValue = "4") int eachPage,
                                                          @RequestParam(value = "category", required = false) List<Integer> categories,
                                                          @RequestParam(value = "skill", required = false) List<Integer> skills,
                                                          @RequestParam(value = "star", required = false, defaultValue = "0") int star,
                                                          @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok().body(courseService.searchCourse(page, eachPage, star, categories, skills, search));
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDTO> createCourse(@Valid CourseDTO courseDTO, Authentication authentication) {
        return ResponseEntity.ok().body(courseService.saveCourse(authentication, courseDTO));
    }

    @PutMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDTO> updateCourse(@Valid CourseDTO courseDTO) {
        return ResponseEntity.ok().body(courseService.updateCourse(courseDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO> deleteCourse(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(courseService.deleteCourse(id));
    }

    @GetMapping(value = "/{id}/student")
    public ResponseEntity<Object> getStudentEnroll(@PathVariable("id") int id,
                                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                                        @RequestParam(value = "eachPage", defaultValue = "4") int eachPage) {
        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok().body(userService.getStudentEnrollCourse(id, page, eachPage));
    }

    @GetMapping(value = "/viewcourses")
    public ResponseEntity<ResponseDTO> viewAllCourse(@RequestParam(value = "page", defaultValue = "1") int page,
                                                     @RequestParam(value = "eachPage", defaultValue = "4") int eachPage) {
        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok().body(courseService.viewAllCourse(page, eachPage));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO> viewCourseDetail(@PathVariable("id") int id) {

        return ResponseEntity.ok().body(courseService.getCourseDetailsById(id));
    }

    @DeleteMapping(value = "/{id}/report")
    public ResponseEntity<ResponseDTO> reportCourse(@PathVariable("id") int id,
                                                    @RequestBody(required = false) String message) {
        return ResponseEntity.ok().body(courseService.reportCourse(id, message));
    }

    @PutMapping(value = "/{id}/open")
    public ResponseEntity<ResponseDTO> openCourse(@PathVariable("id") int id,
                                                    @RequestBody(required = false) String message) {
        return ResponseEntity.ok().body(courseService.openCourse(id, message));
    }

    @GetMapping("/{id}/check-enroll")
    public ResponseEntity<ResponseDTO> checkEnrollCourse(@PathVariable("id") int id, Authentication authentication) {
        return ResponseEntity.ok().body(courseService.checkEnroll(id, authentication));
    }

    @PostMapping(value = "/student/report")
    public ResponseEntity<Object> reportStudent(ReportStudentDTO reportStudentDTO) {

        return ResponseEntity.ok().body(reportStudentService.createReport(reportStudentDTO));
    }

}
