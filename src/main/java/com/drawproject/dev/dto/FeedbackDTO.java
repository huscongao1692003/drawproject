package com.drawproject.dev.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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

    @Max(value = 5, message = "Feedback star must be less than 5")
    @Min(value = 1, message = "Feedback star must be more than 1")
    private int star;

    private int userId;
    private String username;
    private String avatar;
}
