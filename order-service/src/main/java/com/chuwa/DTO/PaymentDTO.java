package com.chuwa.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.cglib.core.Local;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PaymentDTO {


    private UUID orderId;


    private LocalDate createdDate;


    private Date timestamp;


    private String paymentType; // VISA, MASTER, ...


    private String paymentDetails; // card number, cvv, etc.


    private String paymentStatus; // CREATED, PAID, CANCELLED, PENDING


    private Date paymentTime; // PAYMENT SERVICE UPDATE


    private Date sendTime; // time sent to Kafka


    private Double paymentAmount;


    private String paymentCurrency; // Currency type like USD, RMB, etc.
}

