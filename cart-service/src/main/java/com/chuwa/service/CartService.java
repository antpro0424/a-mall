package com.chuwa.service;

import com.chuwa.domain.po.Cart;
import com.chuwa.domain.vo.CartVO;
import com.chuwa.domain.dto.CartFormDTO;

import java.util.Collection;
import java.util.List;

public interface CartService {

    void addItem2Cart(CartFormDTO cartFormDTO);

    List<CartVO> queryMyCarts();

    void removeByItemIds(Collection<String> itemIds);

    void updateById(CartFormDTO cartFormDTO);

    void removeById(String id);
}

