package com.chuwa.service;

import com.chuwa.domain.po.Item;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item saveItem(Item item);

    Optional<Item> getItemById(String id);

    Page<Item> getAllItems(int page, int size);

    void deleteItemById(String id);

    List<Item> findItemsByName(String name);

}
