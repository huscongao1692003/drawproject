package com.drawproject.dev.service;

import com.drawproject.dev.dto.CommentDTO;
import com.drawproject.dev.model.Comment;
import com.drawproject.dev.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<CommentDTO> getAllCommentPost(int postId){
        List<Comment> commentList = commentRepository.findAllByPostsPostId(postId);
        List<CommentDTO> commentDTOS = commentList.stream()
                .map(comment -> mapToCommentDTO(comment))
                .collect(Collectors.toList());
        return commentDTOS;
    }

    public List<CommentDTO> getAllCommentCourse(int courseId){
        List<Comment> commentList = commentRepository.findAllByCoursesCourseId(courseId);
        List<CommentDTO> commentDTOS = commentList.stream()
                .map(comment -> mapToCommentDTO(comment))
                .collect(Collectors.toList());
        return commentDTOS;
    }

    private CommentDTO mapToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentId(comment.getCommentId());
        commentDTO.setCommentValue(comment.getCommentValue());
        // Add more mappings as needed

        return commentDTO;
    }
}
