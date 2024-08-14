package com.chuwa.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    /**
     * 密码，加密存储
     */
    @Column(nullable = false)
    private String password;

    /**
     * 注册手机号
     */
    @Column(nullable = false, unique = true)
    private String phone;

    /**
     * 创建时间
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(nullable = false)
    private LocalDateTime updateTime;

    /**
     * 使用状态（1正常 0冻结）
     */
    @Column(nullable = false)
    private boolean status;

    /**
     * 账户余额
     */
    @Column(nullable = false)
    private Integer balance;
}

