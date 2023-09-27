package com.drawproject.dev.controller;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LessonController {

    @Autowired
    LessonService lessonService;



}
