package com.chuwa.repository;

import com.chuwa.entity.Order;
import com.chuwa.entity.OrderPrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CassandraRepository<Order, OrderPrimaryKey> {
    // Custom query methods can be defined here if needed
}

