package com.drawproject.dev.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {

    private String description;

    private int courseId;

    private int price;

    private int totalPrice;

    private String url = "https://drawproject-production.up.railway.app/api/v1/pay";
}
