package com.drawproject.dev.dto.user_assignment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentWork {
    private int taskId;
    private String taskTitle;
    private String description;
    private String image;
    private String status;
    private int grade;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
