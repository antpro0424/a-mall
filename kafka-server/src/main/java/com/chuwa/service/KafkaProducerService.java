package com.chuwa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Value("your-topic")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void sendMessage(String key, String message) {
        kafkaTemplate.send(topicName, key, message);


    }

}

