package com.chuwa.controller;

import com.chuwa.entity.Order;
import com.chuwa.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v0/orders/user")
public class UserController {
    private final OrderService orderService;

    @Autowired
    public UserController(OrderService orderService) {
        this.orderService = orderService;
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<Slice<Order>> getOrders(@PathVariable UUID userId,
//                                                  @RequestParam("page") int page,
//                                                  @RequestParam("size") int size) {
//        try {
//
//            return new ResponseEntity<>(orderService.findByCustomerId(userId,page,size), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/{userId}")
    public Slice<Order> getOrders(@PathVariable UUID userId,
                                  @RequestParam int page,
                                  @RequestParam int size,
                                  @RequestParam(required = false) String pagingState
    ) {

        return orderService.getPageOfOrders(userId, page, size, pagingState);
    }

}
