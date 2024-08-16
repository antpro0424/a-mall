package com.chuwa.demo.controller;

import com.chuwa.demo.service.KafkaConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KafkaConsumerController {
    @Autowired
    private KafkaConsumerService kafkaConsumerService;


    @GetMapping("/api/v0/kafka/seek")
    public List<String> seek() {

        return kafkaConsumerService.getMessages();
    }
}
