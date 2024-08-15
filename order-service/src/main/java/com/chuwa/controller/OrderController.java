package com.chuwa.controller;

import com.chuwa.DTO.PaymentDTO;
import com.chuwa.entity.Order;
import com.chuwa.entity.OrderPrimaryKey;
import com.chuwa.po.Address;
import com.chuwa.po.OrderStatusEnum;
import com.chuwa.po.Payment;
import com.chuwa.po.UpdateStatus;
import com.chuwa.service.CronService;
import com.chuwa.service.OrderService;
import com.chuwa.util.Feign.ItemService;
import com.chuwa.util.Feign.KafkaClient;
import com.chuwa.util.Feign.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v0/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    private final ItemService itemService;


    private final KafkaService kafkaService;

    @Autowired
    public OrderController(OrderService orderService, ItemService itemService, KafkaService kafkaService) {
        this.orderService = orderService;

        this.itemService = itemService;
        this.kafkaService = kafkaService;
    }

    @PostMapping


    @Transactional
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            // create key
            UUID orderId = UUID.randomUUID();
//            orderId = UUID.fromString("991528c2-e5eb-41d1-93a9-5af5b0cc6b54");
            LocalDate createdDate = LocalDate.now();
//            LocalTime timestamp = LocalTime.now();
            Date timestamp = new Date();
            order.setKey(new OrderPrimaryKey());
            order.getKey().setCreatedDate(createdDate);
            order.getKey().setTimestamp(timestamp);
            order.getKey().setOrderId(orderId);
            order.setSearchId(orderId);

            // add orderId, timestamp, created data to address
            order.getAddress().setOrderId(orderId);
            order.getAddress().setCreatedDate(createdDate);
            order.getAddress().setTimestamp(timestamp);

            // add orderId, timestamp, created data to payment
            order.getPayment().setOrderId(orderId);
            order.getPayment().setCreatedDate(createdDate);
            order.getPayment().setTimestamp(timestamp);

            order.setOrderStatus(OrderStatusEnum.CREATED);
            Order rtnOrder = orderService.createOrder(order);
//            System.out.println(rtnOrder.getKey().getTimestamp());

            System.out.println(itemService.fetchItem("66bd47875448cd4481225ccc"));
            kafkaService.sendMessage(rtnOrder.getKey().getOrderId().toString(), rtnOrder);

            return new ResponseEntity<>(rtnOrder, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }

    @PutMapping
    public ResponseEntity<String> cancelOrder(@RequestBody OrderPrimaryKey key) {
        try {
            String flag = orderService.cancelOrder(key);
            if (flag.compareTo("OK") == 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/payment")
    public ResponseEntity<String> changePayment(@RequestBody PaymentDTO payment) {
        try {
            Order order = orderService.findOrderByKey(new OrderPrimaryKey(payment.getOrderId(), payment.getCreatedDate(), payment.getTimestamp()));
            Payment oldPayment = order.getPayment();
            oldPayment.setPaymentType(payment.getPaymentType());
            oldPayment.setPaymentAmount(payment.getPaymentAmount());
            oldPayment.setPaymentCurrency(payment.getPaymentCurrency());
            oldPayment.setPaymentDetails(payment.getPaymentDetails());

            boolean flag = orderService.updateOrderPayment(oldPayment);
            if (flag)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/address")
    public ResponseEntity<String> changeAddress(@RequestBody Address address) {
        try {

            Order order = orderService.findOrderByKey(new OrderPrimaryKey(address.getOrderId(), address.getCreatedDate(), address.getTimestamp()));
            Address oldAddress = order.getAddress();
            oldAddress.setDetailAddress(address.getDetailAddress());
            oldAddress.setZipCode(address.getZipCode());



            boolean flag = orderService.updateOrderAddress(oldAddress);
            if (flag)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/status")
    public ResponseEntity<String> changeStatus(@RequestBody UpdateStatus updateStatus) {
        try {
            boolean flag = orderService.updateOrderStatus(updateStatus.getKey(), updateStatus.getNewStatus());
            if (flag)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping ("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable UUID orderId) {
        try {

            return new ResponseEntity<>(orderService.findOrderByOrderId(orderId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}

