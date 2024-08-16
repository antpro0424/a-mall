package com.chuwa.repository;

import com.chuwa.domain.po.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    // 根据 userId 查询购物车列表
    List<Cart> findByUserId(Long userId);

    // 统计用户购物车中的商品数量
    int countByUserId(Long userId);

    // 判断某用户购物车中是否已存在某商品
    boolean existsByUserIdAndItemId(Long userId, String itemId);

    // 删除指定用户购物车中的指定商品
    void deleteByUserIdAndItemIdIn(Long userId, Collection<String> itemIds);

    // 增加购物车中某商品的数量
    @Modifying
    @Query("UPDATE Cart c SET c.num = c.num + :num WHERE c.itemId = :itemId AND c.userId = :userId")
    void incrementQuantity(@Param("itemId") String itemId, @Param("userId") Long userId, @Param("num") Integer num);
}
