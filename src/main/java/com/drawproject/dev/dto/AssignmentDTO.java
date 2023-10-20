package com.drawproject.dev.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDTO {

    private int assignmentId;

    @NotBlank(message = "The assignment title must not be blank")
    private String assignmentTitle;

    @Size(min = 10, message = "The topic must be at least 10 characters")
    private String topic;

    private boolean compulsory;

    private int index;

    @NotNull(message = "The lessson must not be null")
    private int lessonId;

    private String status;
}
