package com.miw.gildedrose.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "item_view_count")
public class ItemViewCountEntity extends BaseEntity{

    public ItemViewCountEntity() {
    }

    public ItemViewCountEntity(ItemEntity item, int viewCount) {
        this.item = item;
        this.viewCount = viewCount;
    }

    @JoinColumn(name = "item_id")
    @OneToOne
    private ItemEntity item;

    @Column(name = "view_count")
    private int viewCount;

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

}
