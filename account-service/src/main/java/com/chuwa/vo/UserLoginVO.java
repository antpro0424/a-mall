package com.chuwa.vo;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class UserLoginVO {
    private String token;
    private Long userId;
    private String username;
    private Integer balance;
}
