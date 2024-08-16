package com.chuwa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "order details")
public class OrderDetailDTO {
    @Schema(description = "商品id")
    private String itemId;
    @Schema(description = "商品购买数量")
    private Integer num;
}
