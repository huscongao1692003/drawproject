package com.drawproject.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "report_students")
public class ReportStudent extends BaseEntity {
    @EmbeddedId
    private ReportStudentId id;

    @MapsId("studentId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @MapsId("courseId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;

    @Size(max = 255)
    @Column(name = "message")
    private String message;

    private String image;

    private String status;

}