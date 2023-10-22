package com.drawproject.dev.dto.report;

import com.drawproject.dev.model.ReportStudentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseReport {
    private ReportStudentId reportStudentId;
    private String message;
    private String image;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
