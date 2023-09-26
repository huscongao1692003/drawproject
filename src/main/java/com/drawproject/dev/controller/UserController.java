package com.drawproject.dev.controller;

import com.drawproject.dev.service.CourseService;
import com.drawproject.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    CourseService courseService;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Object> getEnrollCourse(@PathVariable("userId") int userId,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "eachPage", defaultValue = "4") int eachPage) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok(courseService.getCoursesByUser(userId, page, eachPage));
    }

}
