package com.drawproject.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int commentId;

    @NotBlank
    private String commentValue;

    @ManyToOne(fetch = FetchType.LAZY,
            targetEntity = Posts.class)
    @JoinColumn(name = "post_id", referencedColumnName = "postId",nullable = true)
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY,
            targetEntity = Courses.class)
    @JoinColumn(name = "course_id", referencedColumnName = "courseId",nullable = true)
    private Courses courses;

    private String status;

}
