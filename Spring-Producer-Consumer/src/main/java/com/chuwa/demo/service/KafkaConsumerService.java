package com.chuwa.demo.service;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class KafkaConsumerService   {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    List<String> messages = new ArrayList<>();

    @KafkaListener(id = "cat", topics = "your-topic", groupId = "your-group", concurrency = "2")
    public void listen(String message, Acknowledgment acknowledgment) {
//        System.out.println("Received message: " + message);
        addMessage(message);
        acknowledgment.acknowledge();
    }

    public synchronized void  addMessage(String message) {
        messages.add(message);
    }
    public synchronized List<String> getMessages() {
        List<String> rtn = messages.stream().toList();
        messages.clear();
        return rtn;
    }



}
