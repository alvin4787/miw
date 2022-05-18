package com.miw.gildedrose.service;

import com.miw.gildedrose.controller.response.ItemListResponse;
import com.miw.gildedrose.controller.response.ItemViewResponse;
import com.miw.gildedrose.controller.response.OrderResponse;
import com.miw.gildedrose.dao.ItemDao;
import com.miw.gildedrose.dao.ItemViewCountDao;
import com.miw.gildedrose.dao.OrderDao;
import com.miw.gildedrose.dao.UserDao;
import com.miw.gildedrose.entity.ItemEntity;
import com.miw.gildedrose.entity.ItemViewCountEntity;
import com.miw.gildedrose.entity.OrderEntity;
import com.miw.gildedrose.entity.UserEntity;
import com.miw.gildedrose.event.ItemViewedEvent;
import com.miw.gildedrose.exception.CustomException;
import com.miw.gildedrose.exception.ErrorCode;
import com.miw.gildedrose.mapper.ItemMapper;
import com.miw.gildedrose.model.ItemModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemViewCountDao itemViewCountDao;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Value("${gildedrose.surge.threshold}")
    private int surgeThreshold;

    @Value("${gildedrose.surge.offset}")
    private int surgeOffset;

    @Value("${gildedrose.surge.price_increase}")
    private int surgePriceIncrease;

    private static Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    public ItemListResponse getAllItems() {
        return new ItemListResponse(new ItemMapper().toModelList(itemDao.findAll()));
    }

    public ItemViewResponse viewItem(String itemUid) throws CustomException {
        LOGGER.debug("View item with uid: {}", itemUid);
        ItemEntity itemEntity = itemDao.findDistinctFirstByUid(itemUid);
        if(itemEntity == null) {
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }

        ItemViewCountEntity itemViewCountEntity = itemViewCountDao.findDistinctByItem_Id(itemEntity.getId());
        if(itemViewCountEntity == null) {
            itemViewCountEntity = new ItemViewCountEntity();
        }
        itemViewCountEntity.setItem(itemEntity);
        itemViewCountEntity.setViewCount(itemViewCountEntity.getViewCount() + 1);
        itemViewCountEntity = itemViewCountDao.save(itemViewCountEntity);
        eventPublisher.publishEvent(new ItemViewedEvent(this, itemViewCountEntity));
        return new ItemViewResponse(new ItemMapper().toModel(itemEntity, new ItemModel()));
    }

    public void processSurging(ItemViewCountEntity itemViewCountEntity) {
        if(requireSurging(itemViewCountEntity)) {
            ItemEntity itemEntity = itemViewCountEntity.getItem();
            float itemPrice = itemEntity.getPrice();
            float surgedPrice = itemPrice + (itemPrice * ((float)surgePriceIncrease/100f));
            itemEntity.setPrice(surgedPrice);
            itemDao.save(itemEntity);
            itemViewCountEntity.setViewCount(0);
            itemViewCountDao.save(itemViewCountEntity);
        }
    }

    public OrderResponse buyItem(String itemUid, String userEmail) throws CustomException {
        ItemEntity itemEntity = itemDao.findDistinctFirstByUid(itemUid);
        if(itemEntity == null) {
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }
        UserEntity userEntity = userDao.findDistinctFirstByEmail(userEmail);
        return new OrderResponse(processOrder(itemEntity, userEntity).getUid());
    }

    private boolean requireSurging(ItemViewCountEntity itemViewCountEntity) {
        if(itemViewCountEntity.getViewCount() >= surgeThreshold) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime entityLastUpdated = itemViewCountEntity.getLastUpdated();
            if(entityLastUpdated.isAfter(now.minusHours(surgeOffset)) && entityLastUpdated.isBefore(now)) {
                return true;
            }
        }
        return false;
    }

    private OrderEntity processOrder(ItemEntity itemEntity, UserEntity userEntity) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setItem(itemEntity);
        orderEntity.setUser(userEntity);
        orderEntity.setTotalPrice(itemEntity.getPrice());
        return orderDao.save(orderEntity);
    }
}
