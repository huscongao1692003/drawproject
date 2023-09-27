package com.drawproject.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private int lessonId;
    private String url;
    private String name;
    private String typeFile;
}
