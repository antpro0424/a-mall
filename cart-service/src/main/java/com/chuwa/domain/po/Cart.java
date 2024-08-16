package com.chuwa.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity  // JPA 注解，表示这是一个实体类
@Table(name = "cart")  // JPA 注解，指定数据库中的表名
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车条目id
     */
    @Id
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")  // JPA 注解，指定数据库中的列名
    private Long userId;

    /**
     * sku商品id
     */
    @Column(name = "item_id")  // JPA 注解，指定数据库中的列名
    private String itemId;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 商品标题
     */
    private String name;

    /**
     * 价格,单位：元
     */
    private Double unitPrice;

    /**
     * 商品图片
     */
    private String pictureUrl;

    /**
     * 创建时间
     */
    @Column(name = "create_time")  // JPA 注解，指定数据库中的列名
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")  // JPA 注解，指定数据库中的列名
    private LocalDateTime updateTime;
}
