package com.drawproject.dev.dto.course;

import com.drawproject.dev.model.Category;
import com.drawproject.dev.model.Skill;
import com.drawproject.dev.model.Style;

public class CourseDTO {
    private int courseId;
    private String courseTitle;
    private String description;
    private String information;
    private Skill skill;
    private int price;
    private Category category;
    private Style style;
    private String image;

    private String status;
}
