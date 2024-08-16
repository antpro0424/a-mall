package com.chuwa.util;

import com.chuwa.service.CronService;
import com.chuwa.service.OrderService;
import com.chuwa.util.Feign.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

@Component
public class ScheduledTasks {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CronService cronService;

//    @Scheduled(cron = "0/5 * * * * ?")
    public void performTaskEveryFiveMinutes() {
        System.out.println("Task executed every 1 min.");

//        System.out.println(itemService.fetchItem("66bd47875448cd4481225ccc"));

//        Date time = LocalTime.now();
//        Date curTime = time.withSecond(0).withNano(0);
//        Date from = curTime.minusMinutes(5);
//        Date to = curTime.minusMinutes(2);
//
//        cronService.addOrderPaymentToTopic(from, to);


    }
}