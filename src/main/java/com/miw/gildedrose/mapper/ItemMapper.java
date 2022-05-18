package com.miw.gildedrose.mapper;

import com.miw.gildedrose.entity.ItemEntity;
import com.miw.gildedrose.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {

    public ItemModel toModel(ItemEntity entity, ItemModel model) {
        model.setItemName(entity.getName());
        model.setItemDescription(entity.getDescription());
        model.setItemPrice(entity.getPrice());
        model.setUid(entity.getUid());

        return model;
    }

    public List<ItemModel> toModelList(List<ItemEntity> entities) {
        List<ItemModel> modelList = new ArrayList<>();
        entities.forEach(e-> {
            ItemModel model = new ItemModel();
            toModel(e, model);
            modelList.add(model);
        });

        return modelList;
    }
}
