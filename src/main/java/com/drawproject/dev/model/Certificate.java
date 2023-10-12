package com.drawproject.dev.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "certificates")
public class Certificate {
    @Id
    @Column(name = "certificate_id", nullable = false)
    private int certificateId;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", referencedColumnName = "instructorId", nullable = false)
    private Instructor instructor;

}