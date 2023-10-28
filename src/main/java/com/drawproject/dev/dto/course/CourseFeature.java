package com.drawproject.dev.dto.course;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseFeature {
    private int id;
    private String name;
    private Long courseCount;
}
