package com.chuwa.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "item")
public class ItemDTO {
        @Schema(description = "item id")
        private String id;
        @Schema(description = "item name")
        private String name;
        @Schema(description = "universal product code")
        private String upc;
        @Schema(description = "unit price")
        private double unitPrice;
        @Schema(description = "item picture urls")
        private List<String> pictureUrls;
        @Schema(description = "item available units")
        private int availableUnits;
}

