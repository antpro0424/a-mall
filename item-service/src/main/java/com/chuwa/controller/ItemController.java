package com.chuwa.controller;

import com.chuwa.domain.po.Item;
import com.chuwa.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Api(tags = "Item service")
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation(value = "Create or update an item", notes = "Creates a new item or updates an existing item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Item created or updated successfully"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Item> createOrUpdateItem(@RequestBody Item item) {
        Item savedItem = itemService.saveItem(item);
        return new ResponseEntity<>(savedItem, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Item by ID", notes = "Get existing Item with ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Item has found in the service"),
            @ApiResponse(code = 400, message = "Invalid input or not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        Optional<Item> item = itemService.getItemById(id);
        return item.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Get all items", notes = "Retrieves all items")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of items retrieved")
    })
    @GetMapping
    public ResponseEntity<Page<Item>> getAllItems(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<Item> items = itemService.getAllItems(page,size);
        return ResponseEntity.ok(items);
    }

    @ApiOperation(value = "Delete item by ID", notes = "Deletes an item by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Item deleted successfully"),
            @ApiResponse(code = 404, message = "Item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemById(@PathVariable String id) {

        itemService.deleteItemById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Find items by name", notes = "Finds items that match the given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of items matching the name"),
            @ApiResponse(code = 400, message = "Invalid name parameter")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<Item>> findItemsByName(@RequestParam String name,
                                                        @RequestParam(defaultValue="0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Item> items = itemService.findItemsByName(name,pageable);
        return ResponseEntity.ok(items);
    }
}