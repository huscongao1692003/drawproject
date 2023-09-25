package com.drawproject.dev.dto;


import lombok.Data;

@Data
public class UserDTO {
    private int userId;
    private String username;
    private String email;
    private String mobileNum;
    private String status;

}
