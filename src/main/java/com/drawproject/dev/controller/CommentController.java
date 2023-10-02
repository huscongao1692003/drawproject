package com.drawproject.dev.controller;

import com.drawproject.dev.dto.CommentDTO;
import com.drawproject.dev.repository.CommentRepository;
import com.drawproject.dev.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController()
@Slf4j
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentPost(@PathVariable int postId) {
            List<CommentDTO> commentDTO = commentService.getAllCommentPost(postId);

        return ResponseEntity.ok(commentDTO);
    }

    @GetMapping("course/{courseId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentCourse(@PathVariable int courseId) {
        List<CommentDTO> commentDTO = commentService.getAllCommentCourse(courseId);

        return ResponseEntity.ok(commentDTO);
    }

}
