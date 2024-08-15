package com.chuwa.util.Feign;

import com.chuwa.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "KafkaClient", url = "http://localhost:8099/publish")
public interface KafkaClient {
    @PostMapping
    boolean publish(@RequestParam(name="key") String key, @RequestParam(name = "message") String message);
}
