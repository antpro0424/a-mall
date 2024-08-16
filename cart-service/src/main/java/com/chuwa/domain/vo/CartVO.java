package com.chuwa.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 购物车VO实体类
 */
@Data
@Schema(description = "购物车VO实体")
public class CartVO {

    @Schema(description = "购物车条目id", example = "1")
    private String id;

    @Schema(description = "用户id", example = "12345")
    private Long userId;

    @Schema(description = "sku商品id", example = "10001")
    private String itemId;

    @Schema(description = "购买数量", example = "2")
    private Integer num;

    @Schema(description = "商品标题", example = "时尚T恤")
    private String name;

    @Schema(description = "价格，单位：元", example = "59.99")
    private Double unitPrice;

    @Schema(description = "商品图片", example = "http://example.com/image.png")
    private String pictureUrl;

    @Schema(description = "创建时间", example = "2023-05-05T12:30:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", example = "2023-05-06T12:30:00")
    private LocalDateTime updateTime;
}
