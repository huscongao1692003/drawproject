package com.drawproject.dev.controller;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.TopicDTO;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.service.TopicService;
import com.drawproject.dev.ultils.JsonUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping("/{id}/topic")
    public ResponseEntity<ResponseDTO> getTopicByCourse(@PathVariable("id") int courseId) {
        return ResponseEntity.ok().body(topicService.getTopicByCourse(courseId));
    }

//    @PostMapping(value = "/{id}/topic", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<ResponseDTO> createTopic(@RequestPart("files") List<MultipartFile> files, @PathVariable("id") int courseId,
//                                                   @Valid @RequestPart("topic") String topic) {
//        TopicDTO topicDTO = JsonUtils.getJson(topic, TopicDTO.class);
//        return ResponseEntity.ok().body(topicService.createTopic(files, courseId, topicDTO));
//    }

    @PostMapping("/{id}/topic")
    public ResponseEntity<ResponseDTO> createTopic(@PathVariable("id") int courseId,
                                                   @Valid @RequestBody TopicDTO topicDTO) {

        return ResponseEntity.ok().body(topicService.saveTopic(topicDTO, courseId));
    }

    @PutMapping("/topic/{topicId}/close")
    public ResponseEntity<ResponseDTO> updateTopic(@PathVariable("topicId") int topicId) {
        return ResponseEntity.ok().body(topicService.deleteTopic(topicId));
    }

//    @PutMapping(value = "/{id}/topic", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<ResponseDTO> updateTopic(@RequestPart(value = "files", required = false) List<MultipartFile> files, @PathVariable("id") int courseId,
//                                                   @Valid @RequestPart("topic") String topic) {
//        TopicDTO topicDTO = JsonUtils.getJson(topic, TopicDTO.class);
//        return ResponseEntity.ok().body(topicService.updateTopic(files, courseId, topicDTO));
//    }

    @PutMapping(value = "/{id}/topic")
    public ResponseEntity<ResponseDTO> updateTopic(@PathVariable("id") int courseId,
                                                   @Valid @RequestBody TopicDTO topicDTO) {

        return ResponseEntity.ok().body(topicService.saveTopic(topicDTO, courseId));
    }

}
