package com.miw.gildedrose.controller.response;

import com.miw.gildedrose.model.ItemModel;

public class ItemViewResponse {

    public ItemViewResponse(ItemModel item) {
        this.item = item;
    }

    private ItemModel item;

    public ItemModel getItem() {
        return item;
    }

    public void setItem(ItemModel item) {
        this.item = item;
    }
}
