package com.miw.gildedrose.event;

import com.miw.gildedrose.entity.ItemEntity;
import com.miw.gildedrose.entity.ItemViewCountEntity;
import org.springframework.context.ApplicationEvent;

public class ItemViewedEvent extends ApplicationEvent {

    private ItemViewCountEntity itemViewCountEntity;

    public ItemViewedEvent(Object source) {
        super(source);
    }

    public ItemViewedEvent(Object source, ItemViewCountEntity itemViewCountEntity) {
        super(source);
        this.itemViewCountEntity = itemViewCountEntity;
    }

    public ItemViewCountEntity getItemViewCountEntity() {
        return itemViewCountEntity;
    }
}
