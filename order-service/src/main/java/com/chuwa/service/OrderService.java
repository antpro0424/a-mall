package com.chuwa.service;

import com.chuwa.repository.OrderRepository;
import com.chuwa.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    // Other service methods
}

