package com.drawproject.dev.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String status;//status of student owner this course
    private double averageStar;
    private int numLesson;
    private int numStudent;
    private int numQuiz;
}
