package com.drawproject.dev.dto.course;

import lombok.Data;

@Data
public class CourseInstructor {
    private int courseId;
    private String courseTitle;
    private String information;
    private String description;
    private int price;
    private int drawingStyleId;
    private int categoryId;
    private int skillId;
    private String image;
    private String status;
    private int numLesson;
}
