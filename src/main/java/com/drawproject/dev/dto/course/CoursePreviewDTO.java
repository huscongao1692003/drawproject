package com.drawproject.dev.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePreviewDTO {
    private int courseId;
    private String courseTitle;
    private String information;
    private int price;
    private int drawingStyleId;
    private String drawingStyleName;
    private int categoryId;
    private String categoryName;
    private int skillId;
    private String skillName;
    private String image;
    private String status;
    private double averageStar;
    private int numLesson;
    private int numStudent;
    private int instructorId;
    private String avatar;
}
