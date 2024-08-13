package com.chuwa.service.impl;

import com.chuwa.domain.po.Item;
import com.chuwa.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chuwa.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Optional<Item> getItemById(String id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public void deleteItemById(String id) {
        itemRepository.deleteById(id);
    }

    @Override
    public List<Item> findItemsByName(String name) {
        return itemRepository.findByName(name);
    }


}
