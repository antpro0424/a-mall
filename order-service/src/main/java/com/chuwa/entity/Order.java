package com.chuwa.entity;

import com.chuwa.po.Item;
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

    @Column("total_amount")
    private double totalAmount;

    @Column("items")
    private Map<UUID, Item> items; // item_id to quantity

    @Column("payment")
    private Payment payment;

    @Column("order_status")
    private String orderStatus;

    @Column("order_time")
    private Date orderTime;



}

