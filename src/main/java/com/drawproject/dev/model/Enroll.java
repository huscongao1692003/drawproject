package com.drawproject.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "enroll")
public class Enroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int enrollId;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Courses course;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(mappedBy = "enroll")
//    private Set<Process> processes = new LinkedHashSet<>();

}
