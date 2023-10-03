package com.drawproject.dev.dto;


import lombok.Data;

@Data
public class CommentRequestDTO {
    private int postId;
    private int courseId;
    private String commentValue;

}
