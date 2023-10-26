package com.drawproject.dev.dto.instructor;

import lombok.Data;


@Data
public class InstructorDTO {
    private String username;
    private int userId;
    private int numberOfCourse;
    private String avatar;
}
