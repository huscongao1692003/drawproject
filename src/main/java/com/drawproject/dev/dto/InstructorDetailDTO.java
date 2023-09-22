package com.drawproject.dev.dto;

import com.drawproject.dev.dto.course.CoursePreviewDTO;
import com.drawproject.dev.model.Collection;
import com.drawproject.dev.model.Courses;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class InstructorDetailDTO {
    private String userName;
    private String avatar;
    private String mobileNum;
    private String email;
    private String skillName;
    private List<CoursePreviewDTO> courses; // Change the data type to List<CoursePreviewDTO>
}
