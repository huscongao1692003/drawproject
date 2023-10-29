package com.drawproject.dev.dto;

import com.drawproject.dev.dto.course.QuickCourse;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class QuickSearchData {

    private Map<String, Object> tags = new HashMap<>();
    private List<QuickCourse> course;

}
