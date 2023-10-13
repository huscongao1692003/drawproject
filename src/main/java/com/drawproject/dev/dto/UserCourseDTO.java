package com.drawproject.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseDTO {

    private int userID;
    private String email;
    private String avatar;
    private String status;
    private String fullName;
    private ProgressDTO progressDTO;

}
