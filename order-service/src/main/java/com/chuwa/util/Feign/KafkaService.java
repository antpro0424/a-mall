package com.chuwa.util.Feign;

import com.chuwa.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    @Autowired
    private KafkaClient kafkaClient;

    public void sendMessage(String key, Order order) {
        kafkaClient.publish(key, order.toString());
    }
}
