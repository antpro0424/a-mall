package com.chuwa.service.impl;

import com.chuwa.domain.po.Item;
import com.chuwa.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveItem() {
        Item item = new Item();
        item.setId("1");
        item.setName("Item1");
        when(itemRepository.save(item)).thenReturn(item);

        Item savedItem = itemService.saveItem(item);

        assertNotNull(savedItem);
        assertEquals("1", savedItem.getId());
        assertEquals("Item1", savedItem.getName());
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    public void testGetItemById() {
        Item item = new Item();
        item.setId("1");
        when(itemRepository.findById("1")).thenReturn(Optional.of(item));

        Optional<Item> foundItem = itemService.getItemById("1");

        assertTrue(foundItem.isPresent());
        assertEquals("1", foundItem.get().getId());
        verify(itemRepository, times(1)).findById("1");
    }

    @Test
    public void testDeleteItemById() {
        Item item = new Item();
        item.setId("1");
        item.setName("Item1");
        when(itemRepository.findById("1")).thenReturn(Optional.of(item));
        itemService.deleteItemById(item.getId());
        verify(itemRepository, times(1)).deleteById("1");
        assertFalse(itemRepository.existsById(item.getId()));
    }

    @Test
    public void testGetAllItems() {
        Item item1 = new Item();
        item1.setId("1");
        Item item2 = new Item();
        item2.setId("2");

        List<Item> items = Arrays.asList(item1, item2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> itemPage = new PageImpl<>(items, pageable, items.size());

        when(itemRepository.findAll(pageable)).thenReturn(itemPage);

        Page<Item> result = itemService.getAllItems(0, 10);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(itemRepository, times(1)).findAll(pageable);
    }







}
