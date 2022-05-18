package com.miw.gildedrose.controller.response;

import com.miw.gildedrose.model.ItemModel;

import java.util.List;

public class ItemListResponse {

    public ItemListResponse() {
    }

    public ItemListResponse(List<ItemModel> items) {
        this.items = items;
    }

    private List<ItemModel> items;

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }
}
