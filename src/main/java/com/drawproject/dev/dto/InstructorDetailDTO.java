package com.drawproject.dev.dto;

import com.drawproject.dev.dto.course.CoursePreviewDTO;
import lombok.Data;
import java.util.List;


@Data
public class InstructorDetailDTO {
    private String userName;
    private String avatar;
    private String mobileNum;
    private String email;
    private String skillName;
    private List<CoursePreviewDTO> courses;
}
