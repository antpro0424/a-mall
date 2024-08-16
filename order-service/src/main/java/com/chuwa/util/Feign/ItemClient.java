package com.chuwa.util.Feign;

import com.chuwa.po.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ItemService", url = "http://localhost:8083/api/v0/items")
public interface ItemClient {

    @GetMapping("/{id}")
    Item getItem(@PathVariable("id") String id) ;


}
