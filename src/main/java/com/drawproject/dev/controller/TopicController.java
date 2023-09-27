package com.drawproject.dev.controller;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping("/courses/{courseId}/topic")
    public ResponseEntity<ResponseDTO> getTopicByCourse(@PathVariable("courseId") int courseId) {
        return ResponseEntity.ok().body(topicService.getTopicByCourse(courseId));
    }

}
