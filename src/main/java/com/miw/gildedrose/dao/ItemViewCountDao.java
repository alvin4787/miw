package com.miw.gildedrose.dao;

import com.miw.gildedrose.entity.ItemViewCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ItemViewCountDao extends JpaRepository<ItemViewCountEntity, Long> {

    ItemViewCountEntity findDistinctByItem_Id(Long itemId);

    ItemViewCountEntity findDistinctFirstByItem_IdAndLastUpdatedBetween(Long itemId, LocalDateTime start, LocalDateTime end);
}
