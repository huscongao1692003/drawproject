package com.drawproject.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequestDTO {
    private int lessonId;
    private String url;
    private String name;
    private int topicId;
    private String typeFile;
    private int number;
//    private List<AssignmentResponseDTO> listAssignment;
}
