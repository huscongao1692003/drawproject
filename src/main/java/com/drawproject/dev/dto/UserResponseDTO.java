package com.drawproject.dev.dto;

import com.drawproject.dev.model.Roles;
import com.drawproject.dev.model.Skill;
import lombok.Data;
@Data
public class UserResponseDTO {
    private int userId;

    private String username;

    private String email;

    private String mobileNum;

    private String status;

    private Roles roles;

    private Skill skill;
}
