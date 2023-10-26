package com.drawproject.dev.dto.instructor;

import com.drawproject.dev.dto.course.CoursePreviewDTO;
import com.drawproject.dev.model.Collection;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class InstructorDetailDTO {
    private String userName;
    private String avatar;
    private String mobileNum;
    private String email;
    private String skillName;
    private int userId;
    private String bio;
    private String payment;
    private String education;
}
