package com.drawproject.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int instructorId;

    @Size(max = 255)
    @Column(name = "bio")
    private String bio;

    @Size(max = 255)
    @NotNull
    @Column(name = "payment", nullable = false)
    private String payment;

    @Size(max = 255)
    @NotNull
    @Column(name = "education", nullable = false)
    private String education;

    @OneToMany(mappedBy = "instructor")
    private Set<Courses> courses = new LinkedHashSet<>();

}