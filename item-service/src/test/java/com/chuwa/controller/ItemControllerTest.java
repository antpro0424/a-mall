package com.chuwa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.chuwa.domain.po.Item;
import com.chuwa.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ItemControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private ObjectMapper objectMapper; // map it

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateOrUpdateItem() throws Exception {

        Item item = new Item();
        item.setId("1");
        item.setName("test Item");
        item.setUpc("123456789");
        item.setUnitPrice(100.0);
        item.setPictureUrls(List.of("http://example.com/image.jpg"));
        item.setAvailableUnits(10);

        when(itemService.saveItem(any(Item.class))).thenReturn(item);


        String itemJson = objectMapper.writeValueAsString(item);


        String responseJson = mockMvc.perform(post("/v0/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();


        Item responseItem = objectMapper.readValue(responseJson, Item.class);


        assertEquals("1", responseItem.getId());
        assertEquals("test Item", responseItem.getName());
        assertEquals("123456789", responseItem.getUpc());
        assertEquals(100.0, responseItem.getUnitPrice());
        assertEquals(List.of("http://example.com/image.jpg"), responseItem.getPictureUrls());
        assertEquals(10, responseItem.getAvailableUnits());
    }



    @Test
    public void testDeleteItemById() throws Exception {
        String itemId = "1";


        mockMvc.perform(delete("/v0/items/{id}", itemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(itemService, times(1)).deleteItemById(itemId);

        mockMvc.perform(get("/v0/items/{id}", itemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


    }



}
