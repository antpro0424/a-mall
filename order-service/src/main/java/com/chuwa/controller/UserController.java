package com.chuwa.controller;

import com.chuwa.DTO.CassandraPage;
import com.chuwa.DTO.Paginated;
import com.chuwa.entity.Order;
import com.chuwa.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/api/v0/order/users")
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
    public CassandraPage<Order> getPageOfUsers(@PathVariable UUID userId,final @Valid Paginated paginated) {
        return orderService.getPageOfOrders(userId, paginated);
    }
//    public Slice<Order> getOrders(@PathVariable UUID userId,
//                                          @RequestParam int page,
//                                          @RequestParam int size,
//                                  @RequestParam(required = false) String pagingState
//                               ) {
//
//        return orderService.findByCustomerId( userId, page, size, pagingState);
//    }


}
