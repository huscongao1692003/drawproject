package com.drawproject.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Enroll {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int enrollId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Courses course;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(mappedBy = "enroll")
//    private Set<Process> processes = new LinkedHashSet<>();

}
