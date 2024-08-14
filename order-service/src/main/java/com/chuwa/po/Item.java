package com.chuwa.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@UserDefinedType("item")
public class Item {

    @CassandraType(type = CassandraType.Name.UUID)
    private UUID itemId;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String itemName;

    @CassandraType(type = CassandraType.Name.DOUBLE)
    private double unitPrice;

    @CassandraType(type = CassandraType.Name.INT)
    private double quantity;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String currency; // USD, RMD

    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
    private List<String> picList;

}

