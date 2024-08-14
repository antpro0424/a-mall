package com.chuwa.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@UserDefinedType("payment")
public class Payment {

    @CassandraType(type = CassandraType.Name.UUID)
    private UUID orderId;

    @CassandraType(type = CassandraType.Name.UUID)
    private UUID paymentId;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String paymentType; // VISA, MASTER, ...

    @CassandraType(type = CassandraType.Name.TEXT)
    private String paymentDetails; // card number, cvv, etc.

    @CassandraType(type = CassandraType.Name.TEXT)
    private String paymentStatus; // CREATED, PAID, CANCELLED, PENDING

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Date paymentTime; // PAYMENT SERVICE UPDATE

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Date sendTime; // time sent to Kafka

    @CassandraType(type = CassandraType.Name.DOUBLE)
    private Double paymentAmount;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String paymentCurrency; // Currency type like USD, RMB, etc.
}

