package com.miw.gildedrose.service;

import com.miw.gildedrose.controller.response.ItemListResponse;
import com.miw.gildedrose.controller.response.ItemResponse;
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
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

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
        LOGGER.debug("Method to return all items");
        List<ItemModel> modelList = new ItemMapper().toModelList(itemDao.findAll());
        LOGGER.debug("Returning {} number of items", modelList.size());
        return new ItemListResponse(modelList);
    }

    public ItemResponse viewItemByName(String itemName) throws CustomException {
        LOGGER.debug("View item with name: {}", itemName);
        List<ItemEntity> itemEntities = itemDao.findDistinctByName(itemName);
        if(CollectionUtils.isEmpty(itemEntities)) {
            LOGGER.debug("No item found with name = {}", itemName);
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }

        LOGGER.debug("{} item(s) found", itemEntities.size());

        if(itemEntities.size() == 1) {
            increaseViewCountAndPublishEvent(itemEntities.get(0));
            return new ItemViewResponse(new ItemMapper().toModel(itemEntities.get(0), new ItemModel()));
        }

        return new ItemListResponse(new ItemMapper().toModelList(itemEntities));
    }

    public ItemViewResponse viewItem(String itemUid) throws CustomException {
        LOGGER.debug("View item with uid: {}", itemUid);
        ItemEntity itemEntity = itemDao.findDistinctFirstByUid(itemUid);
        if(itemEntity == null) {
            LOGGER.debug("No item found with uid = {}", itemUid);
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }

        increaseViewCountAndPublishEvent(itemEntity);

        return new ItemViewResponse(new ItemMapper().toModel(itemEntity, new ItemModel()));
    }

    public void processSurging(ItemViewCountEntity itemViewCountEntity) {
        if(requireSurging(itemViewCountEntity)) {
            LOGGER.debug("Item with uid = {} requires price surging", itemViewCountEntity.getItem().getUid());
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
        LOGGER.debug("Buying item with uid = {} by email = {}", itemUid, userEmail);
        ItemEntity itemEntity = itemDao.findDistinctFirstByUid(itemUid);
        if(itemEntity == null) {
            LOGGER.debug("Item with uid = {} not found", itemUid);
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }
        UserEntity userEntity = userDao.findDistinctFirstByEmail(userEmail);
        return new OrderResponse(processOrder(itemEntity, userEntity).getUid());
    }

    private void increaseViewCountAndPublishEvent(ItemEntity itemEntity) {
        ItemViewCountEntity itemViewCountEntity = itemViewCountDao.findDistinctByItem_Id(itemEntity.getId());
        if(itemViewCountEntity == null) {
            itemViewCountEntity = new ItemViewCountEntity();
        }
        itemViewCountEntity.setItem(itemEntity);
        itemViewCountEntity.setViewCount(itemViewCountEntity.getViewCount() + 1);
        itemViewCountEntity = itemViewCountDao.save(itemViewCountEntity);
        eventPublisher.publishEvent(new ItemViewedEvent(this, itemViewCountEntity));
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
