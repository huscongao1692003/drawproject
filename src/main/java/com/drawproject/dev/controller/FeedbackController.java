package com.drawproject.dev.controller;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
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

}
