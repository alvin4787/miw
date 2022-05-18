package com.miw.gildedrose.service;

import com.miw.gildedrose.GildedRoseApp;
import com.miw.gildedrose.dao.ItemDao;
import com.miw.gildedrose.dao.ItemViewCountDao;
import com.miw.gildedrose.entity.ItemEntity;
import com.miw.gildedrose.entity.ItemViewCountEntity;
import com.miw.gildedrose.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GildedRoseApp.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemViewCountDao itemViewCountDao;

    @Value("${gildedrose.surge.threshold}")
    private int surgeThreshold;

    @Value("${gildedrose.surge.price_increase}")
    private int surgePriceIncrease;

    @Test
    public void processSurgingTest() throws CustomException {
        ItemEntity itemEntity = itemDao.findDistinctFirstByUid("3nkIuKDjnu");
        float oldPrice = itemEntity.getPrice();
        ItemViewCountEntity itemViewCountEntity = new ItemViewCountEntity(itemEntity, surgeThreshold - 1);
        itemViewCountDao.save(itemViewCountEntity);

        itemService.viewItem("3nkIuKDjnu");
        itemEntity = itemDao.findDistinctFirstByUid("3nkIuKDjnu");

        Assertions.assertEquals(itemEntity.getPrice(), (oldPrice + (oldPrice * (surgePriceIncrease / 100f))));


    }
}
