package com.chuwa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateStatus {
    private OrderPrimaryKey key;
    private OrderStatusEnum newStatus;
}
