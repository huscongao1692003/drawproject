package com.drawproject.dev.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class UserCourseId implements Serializable {
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "user_id")
    private int userId;
}
