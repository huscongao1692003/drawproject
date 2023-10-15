package com.drawproject.dev.controller;

import com.drawproject.dev.dto.FeedbackDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @GetMapping("/courses/{id}/feedback")
    public ResponseEntity<ResponsePagingDTO> getFeedback(@PathVariable("id") int courseId,
                                                         @RequestParam(value = "page") int page,
                                                         @RequestParam(value = "eachPage") int eachPage) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok().body(feedbackService.getFeedback(courseId, page, eachPage));
    }

    @PutMapping("/courses/{id}/feedback")
    public ResponseEntity<ResponseDTO> updateFeedback(@PathVariable("id") int courseId,
                                                      @Valid @RequestBody FeedbackDTO feedbackDTO) {

        return ResponseEntity.ok().body(feedbackService.updateFeedback(courseId, feedbackDTO));
    }

    @PostMapping("/courses/{id}/feedback")
    public ResponseEntity<ResponseDTO> createFeedback(@PathVariable("id") int courseId,
                                                      @RequestBody FeedbackDTO feedbackDTO) {

        return ResponseEntity.ok().body(feedbackService.createFeedback(courseId, feedbackDTO));
    }

    @DeleteMapping("/courses/{id}/feedback/{feedbackId}")
    public ResponseEntity<ResponseDTO> deleteFeedback(@PathVariable("id") int courseId,
                                                      @PathVariable("feedbackId") int feedbackId) {

        return ResponseEntity.ok().body(feedbackService.deleteFeedback(courseId, feedbackId));
    }

}
