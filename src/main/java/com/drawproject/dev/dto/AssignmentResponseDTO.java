package com.drawproject.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResponseDTO {

    private int assignmentId;
    private String assignmentTitle;
    private String topic;
    private boolean force;


}
