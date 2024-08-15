package com.chuwa.repository;

import com.chuwa.domain.po.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item,String> {
    List<Item> findByName(String name);
}
