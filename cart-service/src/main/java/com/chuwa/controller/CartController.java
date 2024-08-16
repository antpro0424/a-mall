package com.chuwa.controller;

import com.chuwa.domain.dto.CartFormDTO;
import com.chuwa.domain.vo.CartVO;
import com.chuwa.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "购物车相关接口", description = "购物车的增删查改功能")
@RestController
@RequestMapping("/api/v0/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "添加商品到购物车")
    @PostMapping
    public void addItem2Cart(@Valid @RequestBody CartFormDTO cartFormDTO) {
        cartService.addItem2Cart(cartFormDTO);
    }

    @Operation(summary = "更新购物车数据")
    @PutMapping
    public void updateCart(@RequestBody CartFormDTO cartFormDTO) {
        cartService.updateById(cartFormDTO);
    }

    @Operation(summary = "删除购物车中商品")
    @DeleteMapping("{id}")
    public void deleteCartItem(
            @Parameter(description = "购物车条目id") @PathVariable("id") String id) {
        cartService.removeById(id);
    }

    @Operation(summary = "查询购物车列表")
    @GetMapping
    public List<CartVO> queryMyCarts() {
        return cartService.queryMyCarts();
    }

    @Operation(summary = "批量删除购物车中商品")
    @Parameter(name = "ids", description = "购物车条目id集合")
    @DeleteMapping
    public void deleteCartItemByIds(@RequestParam("ids") List<String> ids) {
        cartService.removeByItemIds(ids);
    }
}
