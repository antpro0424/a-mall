package com.chuwa.domain.po;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Document(collection="items")
public class Item {

    @Id
    private String id;
    private String name;
    private String upc;
    private double unitPrice;
    private List<String> pictureUrls;
    private int availableUnits;
}