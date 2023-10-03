package com.drawproject.dev.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CollectionDTO {
    @Size(min = 50, message = "Bio need at least 50 characters")
    private String bio;
    @Size(min = 20, message = "Experience need at least 20 characters")
    private String experience;
    @Size(min = 3, message = "List need at least 3 products")
    private List<String> artworks;
    @Size(min = 1, message = "List need at least 1 certificate")
    private List<String> certificates;
}
