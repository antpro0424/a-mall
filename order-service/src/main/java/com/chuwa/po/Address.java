package com.chuwa.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@UserDefinedType("address")
public class Address {
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID orderId;

    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate createdDate;

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Date timestamp;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String zipCode;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String detailAddress;
}
