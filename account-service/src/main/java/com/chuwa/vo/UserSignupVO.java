package com.chuwa.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserSignupVO
{
    private String username;
    private Integer balance;
    private LocalDateTime createTime;
}
