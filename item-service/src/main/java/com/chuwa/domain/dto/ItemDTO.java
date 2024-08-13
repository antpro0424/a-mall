package com.chuwa.domain.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@ApiModel(description = "item")
public class ItemDTO {
        @ApiModelProperty("item id")
        private String id;
        @ApiModelProperty("item name")
        private String name;
        @ApiModelProperty("universal product code")
        private String upc;
        @ApiModelProperty("unit price")
        private double unitPrice;
        @ApiModelProperty("item picture urls")
        private List<String> pictureUrls;
        @ApiModelProperty("item available units")
        private int availableUnits;
}

