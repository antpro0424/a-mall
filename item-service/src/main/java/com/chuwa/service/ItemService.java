package com.chuwa.service;

import com.chuwa.domain.po.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item saveItem(Item item);

    Optional<Item> getItemById(String id);

    List<Item> getAllItems();

    void deleteItemById(String id);

    List<Item> findItemsByName(String name);

}
