package com.drawproject.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class OrderAdminDTO {
    private String username;
    private String fullName;
    private String courseName;
    private String status;
    private String price;
}
