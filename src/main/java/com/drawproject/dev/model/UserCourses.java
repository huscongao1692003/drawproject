package com.drawproject.dev.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_courses")
public class UserCourses {

    @EmbeddedId
    private UserCourseId id;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Courses course;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
}
