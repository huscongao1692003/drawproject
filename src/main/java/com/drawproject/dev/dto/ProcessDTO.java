package com.drawproject.dev.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProcessDTO {
    private ProgressDTO progressDTO;
    private List<Integer> lessons;
}
