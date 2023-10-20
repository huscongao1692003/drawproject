package com.drawproject.dev.dto;

import lombok.Data;

@Data
public class ArtWorkDTO {
    private String artworkId;
    private String image;
    private String status;
    private int categoryId;
    private String categoryName;

}
