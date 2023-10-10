package com.drawproject.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@Entity
public class Posts extends BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int postId;

    @NotBlank(message="Post must not be blank")
    @Size(min=5, message="Post must be at least 5 characters long")
    private String title;


    @OneToOne(fetch = FetchType.EAGER, targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId",nullable = false)
    private Category category;

    @NotBlank(message="description must not be blank")
    @Size(min=10, message="description must be at least 10 characters long")
    private String description;

    @NotNull
    private int readingTime;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String image;

    @NotBlank(message="body must not be blank")
    @Size(min=10, message="body must be at least 10 characters long")
    private String body;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;

//    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
//    private List<Comment> comments;

}
