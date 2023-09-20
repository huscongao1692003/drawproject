package com.drawproject.dev.dto;

import lombok.Data;
import com.drawproject.dev.model.Posts;
import java.util.List;


@Data
public class PostResponseDTO<Posts> {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<Posts> data;
}
