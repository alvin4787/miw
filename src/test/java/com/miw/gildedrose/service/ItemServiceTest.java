package com.miw.gildedrose.service;

import com.miw.gildedrose.GildedRoseApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = GildedRoseApp.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application.yml")
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;
}
