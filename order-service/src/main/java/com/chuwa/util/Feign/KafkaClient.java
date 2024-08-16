package com.chuwa.util.Feign;

import com.chuwa.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "KafkaClient", url = "http://localhost:9099/api/v0/kafka/publish")
public interface KafkaClient {
    @PostMapping
    void publish(@RequestParam(name="key") String key, @RequestParam(name = "message") String message);
}
