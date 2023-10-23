package com.drawproject.dev.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetail {
    private int courseId;
    private String courseTitle;
    private String description;
    private String information;
    private String skillName;
    private int price;
    private String categoryName;
    private String drawingStyleName;
    private String image;
    private String status;
    private String videoIntro;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int instructorId;
    private double averageStar;
    private int numLesson;
    private int numStudent;
    private int numQuiz;
}
