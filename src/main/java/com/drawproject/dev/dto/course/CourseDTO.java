package com.drawproject.dev.dto.course;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private int courseId;

    @NotBlank(message = "Title can not be blank")
    @Size(min = 6, message = "Title must be at least 6 characters long")
    private String courseTitle;

    @NotBlank(message = "Description can not be blank")
    private String description;

    @NotBlank(message = "Information can not be blank")
    @Size(min = 100, message = "Title must be at least 100 characters long")
    private String information;

    @Min(1)
    @NotNull(message = "Price can not be blank")
    private Integer skill;

    @NotNull(message = "Price can not be blank")
    @Min(0)
    private Integer price;

    @Min(1)
    @NotNull(message = "Price can not be blank")
    private Integer category;

    @Min(1)
    @NotNull(message = "Style can not be blank")
    private Integer style;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @NotNull(message = "Image can not be blank")
    private MultipartFile image;

    private String status;
}
