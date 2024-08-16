package com.chuwa.Feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaService {
    @Autowired
    private KafkaClient kafkaClient;

    public List<String> seek() {
        return kafkaClient.seek();
    }
}
