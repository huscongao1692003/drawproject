package com.drawproject.dev.dto;

import com.drawproject.dev.model.Category;
import com.drawproject.dev.model.Comment;
import com.drawproject.dev.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PostDTO {
    @NotBlank(message="Post must not be blank")
    @Size(min=5, message="Post must be at least 5 characters long")
    private String title;


    private int categoryId;

    private String categoryName;

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


    private int userId;

    private String status;
    private String userName;


    private String avatar;
}
