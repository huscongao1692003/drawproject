package com.drawproject.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "assignment")
public class Assignment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assignmentId;

    @NotNull
    @Column(name = "assignment_title", nullable = false)
    private String assignmentTitle;

    @NotNull
    @Column(name = "topic", nullable = false)
    private String topic;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", referencedColumnName = "lessonId" , nullable = false)
    private Lesson lesson;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "assignment")
    private Set<UserAssignment> userAssignments = new LinkedHashSet<>();

    private boolean compulsory;

}