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
    private int price;
    private String style;
    private String category;
    private String skill;
    private String image;
    private double averageStar;
    private int numLesson;
}
