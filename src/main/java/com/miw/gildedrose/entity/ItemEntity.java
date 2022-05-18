package com.miw.gildedrose.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "item", indexes = {
        @Index(name = "item_index_item_name", columnList = "item_name")
})
public class ItemEntity extends BaseEntity {

    public ItemEntity() {
    }

    public ItemEntity(String name, String description, float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Column(name = "item_name")
    private String name;

    @Column(name = "item_description")
    private String description;

    @Column(name = "item_price")
    private float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
