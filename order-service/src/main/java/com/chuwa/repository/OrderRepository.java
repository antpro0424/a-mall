package com.chuwa.repository;

import com.chuwa.entity.Order;
import com.chuwa.entity.OrderPrimaryKey;
import com.chuwa.po.Address;
import com.chuwa.po.OrderStatusEnum;
import com.chuwa.po.Payment;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;


//import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import com.datastax.oss.driver.api.core.cql.ResultSet;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends CassandraRepository<Order, OrderPrimaryKey> {
    // Custom query methods can be defined here if needed
//    @Query("SELECT * FROM orders WHERE customer_id = ?0 AND order_date = ?1 ORDER BY order_time DESC LIMIT 1")
//    Optional<Order> findLatestOrder(UUID customerId, LocalDate orderDate);

    //    @Modifying
    @Query("UPDATE orders SET address = ?2, version = version + 1 WHERE order_id = ?0 AND created_date = ?1 AND timestamp = ?3 IF version = ?4 AND status = 'CREATED'")
    ResultSet updateAddressIfVersionMatches(UUID orderId, LocalDate orderDate, Address newAddress, Date timestamp, int expectedVersion);

    //    @Modifying
    @Query("UPDATE orders SET payment = ?2, version = version + 1 WHERE order_id = ?0 AND created_date = ?1 AND timestamp = ?3 IF version = ?4 AND status = 'CREATED'")
    ResultSet updatePaymentIfVersionMatches(UUID orderId, LocalDate orderDate, Payment newPayment,Date timestamp, int expectedVersion);

    //    @Modifying
    @Query("UPDATE orders SET status = ?2, version = version + 1 WHERE order_id = ?0 AND created_date = ?1  AND timestamp = ?3 IF version = ?4")
    ResultSet updateStatusIfVersionMatches(UUID orderId, LocalDate orderDate, OrderStatusEnum newOrderStatusEnum, Date timestamp,int expectedVersion);

    @Query("SELECT * FROM orders WHERE order_id = ?0 ALLOW FILTERING")
    Optional<Order> findBySearchId(UUID orderId);

//    @Query("SELECT * FROM orders WHERE customer_id = ?0 ALLOW FILTERING")
//    Optional<List<Order>> findByCustomerId(UUID orderId);

    @Query("SELECT * FROM orders WHERE customer_id = ?0 ALLOW FILTERING")
    Slice<Order> findByCustomerId(UUID orderId, CassandraPageRequest pageable);
}

