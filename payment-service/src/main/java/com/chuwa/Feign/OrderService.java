package com.chuwa.Feign;

import com.chuwa.dto.UpdateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderClient orderClient;

    public void updateStatus(UpdateStatus status) {
        orderClient.updateStatus(status);
    }

}
