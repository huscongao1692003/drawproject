package com.drawproject.dev.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "artworks")
public class Artwork {
    @Id
    @Column(name = "artwork_id", nullable = false)
    private Integer artworkId;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id",referencedColumnName = "instructorId", nullable = false)
    private Instructor instructor;

}