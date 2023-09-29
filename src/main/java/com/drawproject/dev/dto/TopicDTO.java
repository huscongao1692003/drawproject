package com.drawproject.dev.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {

    private int topicId;

    @NotBlank(message = "Topic title cannot be empty")
    @Size(min = 4, message = "Topic title must be at least 4 characters")
    private String topicTitle;

    private List<LessonDTO> lessons;
}
