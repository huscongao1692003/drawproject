package com.drawproject.dev.controller;


import com.drawproject.dev.model.Enroll;
import com.drawproject.dev.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService;

//    @GetMapping("/{userId}")
//    public ResponseEntity<List<Enroll>> getAllOrderUser(@PathVariable int userId, Authentication authentication){
//
//    }


}
