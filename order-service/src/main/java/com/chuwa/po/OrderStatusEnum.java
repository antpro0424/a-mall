package com.chuwa.po;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    CREATED(1),
    PAID(2),
    CANCELLED(3),
    PENDING(4);

    private final int value;

    public static OrderStatusEnum fromValue(int value) {
        for (OrderStatusEnum status : OrderStatusEnum.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
