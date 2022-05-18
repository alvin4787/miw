package com.miw.gildedrose.event;

import com.miw.gildedrose.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ItemViewedEventListener implements ApplicationListener<ItemViewedEvent> {

    @Autowired
    private ItemService itemService;

    private static Logger LOGGER = LoggerFactory.getLogger(ItemViewedEventListener.class);

    @Override
    public void onApplicationEvent(ItemViewedEvent event) {
        LOGGER.debug("Received event for item uid: {}", event.getItemViewCountEntity().getItem().getUid());
        itemService.processSurging(event.getItemViewCountEntity());

    }
}
