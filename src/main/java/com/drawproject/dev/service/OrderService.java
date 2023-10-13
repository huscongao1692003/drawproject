package com.drawproject.dev.service;

import com.drawproject.dev.model.Enroll;
import com.drawproject.dev.model.Orders;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.EnrollRepository;
import com.drawproject.dev.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    EnrollRepository enrollRepository;

    public Orders createOrder(Orders order) {
        return orderRepository.save(order);
    }
    public Enroll createEnroll(Enroll enroll) {
        return enrollRepository.save(enroll);
    }

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Orders> getUserOrders(User user) {
        return orderRepository.findOrdersWithUserAndCourse(user);
    }


}
