package com.chuwa.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "新增购物车商品表单实体")
public class CartFormDTO {

    @Schema(description = "用户id", example = "12345")
    private Long userId;

    @Schema(description = "商品id", example = "10001")
    private String itemId;

    @Schema(description = "购买数量", example = "2")
    private Integer num;

    @Schema(description = "商品标题", example = "最新款手机")
    private String name;

    @Schema(description = "价格,单位：元", example = "1999.00")
    private Double unitPrice;

    @Schema(description = "商品图片", example = "http://example.com/image.png")
    private List<String> pictureUrls;
}
