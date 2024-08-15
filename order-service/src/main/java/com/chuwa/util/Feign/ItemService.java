package com.chuwa.util.Feign;

import com.chuwa.po.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemClient itemService;

    public Item fetchItem(String itemId) {
        return itemService.getItem(itemId);
    }
}
