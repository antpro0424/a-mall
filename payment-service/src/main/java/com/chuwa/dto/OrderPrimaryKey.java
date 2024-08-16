package com.chuwa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderPrimaryKey implements Serializable {
    private UUID orderId;
    private LocalDate createdDate;
    private Date timestamp;


}