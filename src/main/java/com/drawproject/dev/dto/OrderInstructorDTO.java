package com.drawproject.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class OrderInstructorDTO {
    private String username;
    private String fullName;
    private String courseName;
    private String status;
    private int price;
    public OrderInstructorDTO(String username, String fullName, String courseName, String status, int price) {
        this.username = username;
        this.fullName = fullName;
        this.courseName = courseName;
        this.status = status;
        this.price = price;
    }
}
