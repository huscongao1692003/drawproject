package com.drawproject.dev.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePagingDTO {
    private HttpStatus status;
    private String message;
    private long totalElements;
    private int page;
    private int totalPage;
    private int perPage;
    private Object data;

}