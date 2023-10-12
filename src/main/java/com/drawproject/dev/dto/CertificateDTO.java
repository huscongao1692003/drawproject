package com.drawproject.dev.dto;

import com.drawproject.dev.model.Style;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDTO {
    private int certificateId;
    private String image;
    private Style style;
    private String status;
}
