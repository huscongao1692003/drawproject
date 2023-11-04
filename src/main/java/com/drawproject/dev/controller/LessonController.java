package com.drawproject.dev.controller;

import com.drawproject.dev.dto.LessonDTO;
import com.drawproject.dev.dto.LessonRequestDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/lessons")
public class LessonController {

    @Autowired
    LessonService lessonService;

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> createLesson(@RequestParam(value = "file", required = false) MultipartFile file, LessonRequestDTO lessonRequestDTO) {
        return ResponseEntity.ok().body(lessonService.createLesson(file, lessonRequestDTO));
    }

    @PutMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> updateLesson(@RequestParam(value = "file", required = false) MultipartFile file, int topicId, LessonDTO lessonDTO) {
        return ResponseEntity.ok().body(lessonService.updateLesson(file, lessonDTO, topicId));
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseDTO> deleteLesson(int lessonId) {
        return ResponseEntity.ok().body(lessonService.deleteLesson(lessonId));
    }

}
