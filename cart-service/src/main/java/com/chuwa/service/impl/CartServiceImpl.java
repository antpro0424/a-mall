package com.chuwa.service.impl;

import com.chuwa.domain.dto.CartFormDTO;
import com.chuwa.domain.po.Cart;
import com.chuwa.domain.vo.CartVO;
import com.chuwa.exception.BizIllegalException;
import com.chuwa.repository.CartRepository;
import com.chuwa.service.CartService;
import com.chuwa.utils.BeanUtils;
import com.chuwa.utils.CollUtils;
import com.chuwa.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public void addItem2Cart(CartFormDTO cartFormDTO) {
        // 1.获取登录用户
        Long userId = UserContext.getUser();

        // 2.判断是否已经存在
        if (checkItemExists(cartFormDTO.getItemId(), userId)) {
            // 2.1.存在，则更新数量
            cartRepository.incrementQuantity(cartFormDTO.getItemId(), userId, cartFormDTO.getNum());
            return;
        }
        // 2.2.不存在，判断是否超过购物车数量
        checkCartsFull(userId);

        // 3.新增购物车条目
        Cart cart = BeanUtils.copyBean(cartFormDTO, Cart.class);
        cart.setPictureUrl(cartFormDTO.getPictureUrls().get(0));
        cart.setUserId(userId);
        cart.setId(UUID.randomUUID().toString());  // 生成 String 类型的 id
        cart.setCreateTime(LocalDateTime.now());
        cart.setUpdateTime(LocalDateTime.now());
        cartRepository.save(cart);
    }

    @Override
    public void updateById(CartFormDTO cartFormDTO) {
        Cart cart = BeanUtils.copyBean(cartFormDTO, Cart.class);
        cart.setPictureUrl(cartFormDTO.getPictureUrls().get(0));
        // 更新购物车条目的更新时间
        cart.setUpdateTime(LocalDateTime.now());

        // 保存更新后的购物车条目
        cartRepository.save(cart);
    }

    @Override
    public void removeById(String id) {
        // 根据ID删除购物车条目
        cartRepository.deleteById(id);
    }

    @Override
    public List<CartVO> queryMyCarts() {
        // 1.查询我的购物车列表
        Long userId = UserContext.getUser();
        List<Cart> carts = cartRepository.findByUserId(userId);
        if (CollUtils.isEmpty(carts)) {
            return CollUtils.emptyList();
        }

        // 2.转换为 VO
        return BeanUtils.copyList(carts, CartVO.class);
    }

    @Override
    public void removeByItemIds(Collection<String> itemIds) {
        Long userId = UserContext.getUser();
        cartRepository.deleteByUserIdAndItemIdIn(userId, itemIds);
    }

    private void checkCartsFull(Long userId) {
        int count = cartRepository.countByUserId(userId);
        if (count >= 10) {
            throw new BizIllegalException("用户购物车条目不能超过10");
        }
    }

    private boolean checkItemExists(String itemId, Long userId) {
        return cartRepository.existsByUserIdAndItemId(userId, itemId);
    }
}
