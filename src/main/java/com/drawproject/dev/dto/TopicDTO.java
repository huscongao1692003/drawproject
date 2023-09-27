package com.drawproject.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
    private int topicId;
    private String topicTitle;
    private List<LessonDTO> lessons;
}
