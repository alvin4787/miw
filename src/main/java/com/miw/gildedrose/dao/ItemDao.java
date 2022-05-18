package com.miw.gildedrose.dao;

import com.miw.gildedrose.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDao extends JpaRepository<ItemEntity, Long> {

    ItemEntity findDistinctFirstByUid(String uid);

    @Query("SELECT item.price from ItemEntity item WHERE item.id = :itemId")
    Integer getItemPrice(@Param("itemId") Long itemId);
}
