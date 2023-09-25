package com.drawproject.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private int feedbackId;
    private String feedbackInformation;
    private String status;
    private int star;
    private int userId;
    private String username;
    private String avatar;
}
