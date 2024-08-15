package com.chuwa.entity;

import com.chuwa.po.Address;
import com.chuwa.po.Item;
import com.chuwa.po.OrderStatusEnum;
import com.chuwa.po.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("orders")
public class Order {

    @PrimaryKey
    private OrderPrimaryKey key;

    @Column("search_id")
    private UUID searchId; // for findByOrderId

    @Column("customer_id")
    private UUID customerId;

    @Column("total_amount")
    private double totalAmount;

//    @Column("order_time")
//    private Date orderTime;

    @Column("items")
    private Map<String, Item> items; // item_id to quantity


    @Column("status")
    private OrderStatusEnum orderStatus;

    @Column("address")
    private Address address;

    @Column("payment")
    private Payment payment;

    @Column("version")
    private int version;


}

