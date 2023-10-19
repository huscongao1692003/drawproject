package com.drawproject.dev.dto.user_assignment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionDTO {
    private int taskId;
    private String fullName;
    private String courseName;
    private String lessonName;
    private String status;  // trạng cu assignmetn đã chấm hay chưa?
    private LocalDateTime createdAt;    //UserAssignment
    private LocalDateTime updatedAt;    //UserAssignment
}
