package com.drawproject.dev.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {

    private String description;

    private String cancelUrl;

    private String successUrl;
}
