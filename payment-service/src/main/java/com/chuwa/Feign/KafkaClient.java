package com.chuwa.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "KafkaClient", url = "http://localhost:9099/api/v0/kafka/seek")
public interface KafkaClient {
    @GetMapping
    List<String> seek();
}