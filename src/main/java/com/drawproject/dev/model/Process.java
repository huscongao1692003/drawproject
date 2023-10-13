package com.drawproject.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "process")
public class Process extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int processId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enroll_id", referencedColumnName = "enrollId")
    private Enroll enroll;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id", referencedColumnName = "lessonId")
    private Lesson lesson;

    @Column(name = "status")
    private String status;

}
