package com.drawproject.dev.dto.course;

import lombok.Data;

@Data
public class CourseFeatureDTO {
    private int instructorId;
    private double averageStar;
    private int numLesson;
    private int numStudent;
    private int numQuiz;
}
