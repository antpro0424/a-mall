package com.chuwa.mapper;
import com.chuwa.domain.po.Item;
import com.chuwa.domain.dto.ItemDTO;
import org.springframework.stereotype.Component;



@Component
public class itemMapper {

    // Convert Item to ItemDTO
    public ItemDTO toItemDTO(Item item) {
        if (item == null) {
            return null;
        }
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(item.getName());
        itemDTO.setUpc(item.getUpc());
        itemDTO.setUnitPrice(item.getUnitPrice());
        itemDTO.setPictureUrls(item.getPictureUrls());
        itemDTO.setAvailableUnits(item.getAvailableUnits());
        return itemDTO;
    }

    // Convert ItemDTO to Item
    public Item toItem(ItemDTO itemDTO) {
        if (itemDTO == null) {
            return null;
        }
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setUpc(itemDTO.getUpc());
        item.setUnitPrice(itemDTO.getUnitPrice());
        item.setPictureUrls(itemDTO.getPictureUrls());
        item.setAvailableUnits(itemDTO.getAvailableUnits());
        return item;
    }

}
