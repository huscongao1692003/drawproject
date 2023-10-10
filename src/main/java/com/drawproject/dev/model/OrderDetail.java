//package com.drawproject.dev.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name = "order_details")
//public class OrderDetail {
//
//    @EmbeddedId
//    private OrderDetailId id;
//
//    @ManyToOne
//    @MapsId("courseId")
//    @JoinColumn(name = "course_id")
//    private Courses course;
//
//    @ManyToOne
//    @MapsId("orderId")
//    @JoinColumn(name = "orders_id")
//    private Orders order;
//}
