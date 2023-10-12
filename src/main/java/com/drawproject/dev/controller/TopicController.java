package com.drawproject.dev.controller;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.TopicDTO;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping("/courses/{id}/topic")
    public ResponseEntity<ResponseDTO> getTopicByCourse(@PathVariable("id") int courseId) {
        System.out.println(courseId);
        return ResponseEntity.ok().body(topicService.getTopicByCourse(courseId));
    }

    @PostMapping("/courses/{id}/topic")
    public ResponseEntity<ResponseDTO> createTopic(@PathVariable("id") int courseId,
                                              @Valid @RequestBody TopicDTO topicDTO) {

        return ResponseEntity.ok().body(topicService.createTopic(courseId, topicDTO));
    }

}
