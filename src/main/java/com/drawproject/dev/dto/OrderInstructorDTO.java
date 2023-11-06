package com.drawproject.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderInstructorDTO {
    private String username;
    private String fullName;
    private String courseName;
    private String status;
    private String price;
}
