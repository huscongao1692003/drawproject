package com.drawproject.dev.dto.instructor;

import lombok.Data;

import java.util.List;

@Data
public class InstructorProfile {
    private int instructorId;
    private String bio;
    private String payment;
    private String education;
    private List<Integer> styles;
}
