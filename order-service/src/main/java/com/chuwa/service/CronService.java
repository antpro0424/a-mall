package com.chuwa.service;

import com.chuwa.entity.Order;
import com.chuwa.po.OrderStatusEnum;
import com.chuwa.repository.OrderRepository;
import com.chuwa.util.Feign.KafkaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class CronService {
    private final OrderRepository orderRepository;
    private final KafkaService kafkaService;
    @Autowired
    public CronService(OrderRepository orderRepository, KafkaService kafkaService) {
        this.orderRepository = orderRepository;
        this.kafkaService = kafkaService;
    }



    @Transactional
    public void addOrderPaymentToTopic(Date from, Date to) {
        System.out.println("from "+ from);
        System.out.println("to "+ to);

        List<Order> orders = orderRepository.findAllByOrderDateBetween(from, to);

        orders.forEach(System.out::println);

        orders.forEach(order -> {
            try {
                kafkaService.sendMessage(order.getKey().getOrderId().toString(),order);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            orderRepository.updateStatusIfVersionStatusMatches(
                    order.getKey().getOrderId(),
                    order.getKey().getCreatedDate(),
                    order.getKey().getTimestamp(),
                    OrderStatusEnum.CREATED,
                    OrderStatusEnum.PENDING,
                    order.getVersion()
                    );

        });


    }
}
