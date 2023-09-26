package com.drawproject.dev.dto.course;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class CourseUpdateDTO {
    @Size(min = 6, message = "Title must be at least 6 characters long")
    private String courseTitle;

    @Size(min = 6, message = "Title must be at least 6 characters long")
    private String description;

    @Size(min = 100, message = "Title must be at least 100 characters long")
    private String information;

    @Min(value = 1, message = "Not existed skill")
    private Integer skill;

    @Min(value = 0, message = "Price have to positive")
    private Integer price;

    @Min(value = 1, message = "Not existed category")
    private Integer category;

    @Min(value = 1, message = "Not existed style")
    private Integer style;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private MultipartFile image;

    private String status;
}
