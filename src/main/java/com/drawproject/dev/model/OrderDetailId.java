package com.drawproject.dev.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class OrderDetailId implements Serializable {

        @Column(name = "course_id")
        private int courseId;

        @Column(name = "orders_id")
        private int orderId;

    }

