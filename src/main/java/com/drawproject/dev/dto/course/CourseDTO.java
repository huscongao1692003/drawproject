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

    @NotBlank(message = "Title can not be blank")
    @Size(min = 6, message = "Title must be at least 6 characters long")
    private String courseTitle;

    @NotBlank(message = "Description can not be blank")
    private String description;

    @NotBlank(message = "Information can not be blank")
    @Size(min = 100, message = "Title must be at least 100 characters long")
    private String information;

    @Min(value = 1, message = "Not existed skill")
    @NotNull(message = "Price can not be blank")
    private Integer skill;

    @NotNull(message = "Price can not be blank")
    @Min(value = 0, message = "Price must be positive")
    private Integer price;

    @Min(value = 1, message = "Not existed category")
    @NotNull(message = "Category can not be blank")
    private Integer category;

    @Min(value = 1, message = "Not existed category")
    @NotNull(message = "Style can not be blank")
    private Integer style;

    private String status;
}
