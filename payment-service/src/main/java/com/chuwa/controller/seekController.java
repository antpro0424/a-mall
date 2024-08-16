package com.chuwa.controller;

import com.chuwa.Feign.KafkaClient;
import com.chuwa.Feign.OrderService;
import com.chuwa.dto.OrderPrimaryKey;
import com.chuwa.dto.OrderStatusEnum;
import com.chuwa.dto.UpdateStatus;
import com.chuwa.util.ScheduledTask;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v0/kafka")
public class seekController {
    @Autowired
    private KafkaClient kafkaClient;
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<String> seek(){


        List<String> msg = kafkaClient.seek();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            for (String m : msg) {
                JsonNode node = objectMapper.readTree(m);
                UUID id = UUID.fromString(node.get("searchId").textValue());

                JsonNode key = node.get("key");

                UUID orderId = UUID.fromString(key.get("orderId").textValue());
                long timestamp = key.get("timestamp").longValue();
                Date date = new Date(timestamp);

                String localDateString = key.get("createdDate").toString();
                String DateString = localDateString.substring(1, localDateString.length()-1);
                String[] dateArray = DateString.split(",");

                LocalDate localDate = LocalDate.of(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]));

                OrderPrimaryKey orderPrimaryKey = new OrderPrimaryKey();
                orderPrimaryKey.setOrderId(orderId);
                orderPrimaryKey.setCreatedDate(localDate);
                orderPrimaryKey.setTimestamp(date);

                UpdateStatus status = new UpdateStatus();
                status.setNewStatus(OrderStatusEnum.PAID);
                status.setKey(orderPrimaryKey);

                orderService.updateStatus(status);

            }
        } catch (Exception e) {
            System.out.println("Mapper error");;
        }
        return msg;
    }
}
