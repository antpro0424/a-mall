package com.chuwa.Feign;


import com.chuwa.dto.UpdateStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "OrderService", url = "http://localhost:8084/api/v0/orders")
public interface OrderClient {
    @PutMapping("/status")
    void updateStatus(@RequestBody UpdateStatus status); // update to PAID
}
