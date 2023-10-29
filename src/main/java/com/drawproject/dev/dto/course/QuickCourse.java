package com.drawproject.dev.dto.course;

import lombok.Data;

@Data
public class QuickCourse {
    private int courseId;
    private String courseTitle;
    private String image;
    private int instructorId;
    private String fullName;
}
