package com.drawproject.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressDTO {

    private int progress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
}
