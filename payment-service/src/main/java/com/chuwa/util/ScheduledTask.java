package com.chuwa.util;

import com.chuwa.Feign.KafkaClient;
import com.chuwa.Feign.OrderService;
import com.chuwa.dto.OrderPrimaryKey;
import com.chuwa.dto.OrderStatusEnum;
import com.chuwa.dto.UpdateStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class ScheduledTask {

    @Autowired
    private OrderService orderService;

    @Autowired
    private KafkaClient kafkaClient;

    @Scheduled(cron = "0 * * * * ?")
    public void getPaymentFromTopic(){
        System.out.println("Task executed every 1 min.");
        List<String> msg = kafkaClient.seek();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            for (String m : msg) {
                JsonNode node = objectMapper.readTree(m);
                UUID id = UUID.fromString(node.get("searchId").textValue());
                System.out.println("deal with "+ id);
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


    }
}
