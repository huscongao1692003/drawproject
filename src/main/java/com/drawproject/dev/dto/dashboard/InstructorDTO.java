package com.drawproject.dev.dto.dashboard;

import lombok.Data;

import java.util.List;

@Data
public class InstructorDTO {
    private int numOfStudent;
    private double star;
    private int numOfCourseOpen;
    private int numOfCourse;
    private float totalIncome;
    private int numOfPost;
    private List<Object[]> numOfCourseByStyle;
    private List<Object[]> numOfCourseByCategory;
}
