package com.drawproject.dev.service;

import com.drawproject.dev.model.OrderDetail;
import com.drawproject.dev.model.Orders;
import com.drawproject.dev.repository.OrderDetailRepository;
import com.drawproject.dev.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public Orders createOrder(Orders order) {
        return orderRepository.save(order);
    }

    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

}
