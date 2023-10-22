package com.drawproject.dev.dto.report;

import com.drawproject.dev.model.Courses;
import com.drawproject.dev.model.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReportStudentDTO {
    private int studentId;
    private int courseId;
    private String message;
    private MultipartFile image;
}
