package com.chuwa.entity;

import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@PrimaryKeyClass
public class OrderPrimaryKey implements Serializable {

    @PrimaryKeyColumn(name = "order_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID orderId;

    @PrimaryKeyColumn(name = "created_date", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private LocalDate createdDate;

    @PrimaryKeyColumn(name = "timestamp", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date timestamp;


}

