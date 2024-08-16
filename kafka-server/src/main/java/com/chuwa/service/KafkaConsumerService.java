package com.chuwa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService   {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);


    @KafkaListener(id = "cat", topics = "your-topic", groupId = "your-group", concurrency = "2")
    public void listen(String message, Acknowledgment acknowledgment) {
        System.out.println("Received message: " + message);
        acknowledgment.acknowledge();
    }






}