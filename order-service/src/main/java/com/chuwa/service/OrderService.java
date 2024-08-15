package com.chuwa.service;

import com.chuwa.entity.OrderPrimaryKey;
import com.chuwa.po.Address;
import com.chuwa.po.OrderStatusEnum;
import com.chuwa.po.Payment;
import com.chuwa.repository.OrderRepository;
import com.chuwa.entity.Order;
import com.datastax.oss.driver.api.core.cql.PagingState;
import com.datastax.oss.driver.api.core.cql.Row;
import jakarta.annotation.Nullable;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;


import com.datastax.oss.driver.api.core.cql.ResultSet;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /*
     * To do
     * check Item-service, make sure inventory are valid and decrease inventory
     * */

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findOrderByOrderId(UUID orderId) {
        Optional<Order> order = orderRepository.findBySearchId(orderId);
        return order.orElse(null);

    }

//    public List<Order> findByCustomerId(UUID orderId) {
//        Optional<List<Order>> order = orderRepository.findByCustomerId(orderId);
//        return order.orElse(null);
//
//    }



    public Order findOrderByKey(OrderPrimaryKey key) {
        return orderRepository.findById(key).orElseThrow(() -> new RuntimeException("Order not found"));
    }


    /*
     * To do
     * update Item-service
     * Payment Service
     * */

    public String cancelOrder(OrderPrimaryKey key) {

//        Order findOrder = orderRepository.findLatestOrder(order.getOrderId(), order.getKey().getOrderDate()).orElseThrow(() -> new RuntimeException("Order not found"));
        Order findOrder = orderRepository.findById(key).orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            if (findOrder.getOrderStatus() == OrderStatusEnum.CANCELLED)
                return "Invalid Operation";
            else if (findOrder.getOrderStatus() == OrderStatusEnum.PAID) {
                // call payment service
            } else if (findOrder.getOrderStatus() == OrderStatusEnum.PENDING) {
                // already submit, send message to Kafka
            } else if (findOrder.getOrderStatus() == OrderStatusEnum.CREATED) {
                // not send to Kafka

            }
            return "dont know";
        } catch (Exception e) {
            return "failed to cancel order";
        }
    }


    public boolean updateOrderPayment(Payment payment) {
        UUID order_id = payment.getOrderId();
        LocalDate createDate = payment.getCreatedDate();
        Date timestamp = payment.getTimestamp();

        Order order = orderRepository.findById(new OrderPrimaryKey(order_id,createDate,timestamp)).orElseThrow(() -> new RuntimeException("Order not found"));

        int version = order.getVersion();
        ResultSet resultSet =  orderRepository.updatePaymentIfVersionMatches(order_id,createDate,payment, timestamp,version);
        // Check if the operation was applied
        Row row = resultSet.one();
        if (row != null && row.getBoolean("[applied]")) {
            // The condition was met, and the update was successful
            return true;
        } else {
            // The condition was not met (e.g., the version did not match)
            return false;
        }
    }

    public boolean updateOrderAddress(Address address) {
        UUID order_id = address.getOrderId();
        LocalDate createDate = address.getCreatedDate();
        Date timestamp = address.getTimestamp();

        Order order = orderRepository.findById(new OrderPrimaryKey(order_id,createDate,timestamp)).orElseThrow(() -> new RuntimeException("Order not found"));

        int version = order.getVersion();
        ResultSet resultSet =  orderRepository.updateAddressIfVersionMatches(order_id,createDate,address, timestamp, version);
        // Check if the operation was applied
        Row row = resultSet.one();
        if (row != null && row.getBoolean("[applied]")) {
            // The condition was met, and the update was successful
            return true;
        } else {
            // The condition was not met (e.g., the version did not match)
            return false;
        }
    }

    public boolean updateOrderStatus(OrderPrimaryKey key, OrderStatusEnum newOrderStatus) {
        UUID order_id = key.getOrderId();
        LocalDate createDate = key.getCreatedDate();
        Date timestamp = key.getTimestamp();
        Order order = orderRepository.findById(key).orElseThrow(() -> new RuntimeException("Order not found"));

        int version = order.getVersion();
        ResultSet resultSet =  orderRepository.updateStatusIfVersionMatches(order_id,createDate,newOrderStatus, timestamp, version);
        // Check if the operation was applied
        Row row = resultSet.one();
        if (row != null && row.getBoolean("[applied]")) {
            // The condition was met, and the update was successful
            return true;
        } else {
            // The condition was not met (e.g., the version did not match)
            return false;
        }
    }


    // Pagination
    public Slice<Order> getPageOfOrders(UUID userId, int page, int size, String pagingState) {
//        Pageable pageable = Pageable.ofSize(size);

        if (pagingState == null)
            page = 0;
        PageRequest pageable = PageRequest.of(page, size);
        if (page == 0)
            return orderRepository.findByCustomerId(userId, pageable);

        CassandraPageRequest pageRequest = CassandraPageRequest.of(pageable, PagingState.fromString(pagingState).getRawPagingState());
        return orderRepository.findByCustomerId(userId, pageable);
    }

}

