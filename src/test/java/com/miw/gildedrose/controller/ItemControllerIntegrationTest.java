package com.miw.gildedrose.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miw.gildedrose.GildedRoseApp;
import com.miw.gildedrose.controller.response.ItemListResponse;
import com.miw.gildedrose.dao.ItemDao;
import com.miw.gildedrose.dao.UserDao;
import com.miw.gildedrose.entity.UserEntity;
import com.miw.gildedrose.model.ItemModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GildedRoseApp.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private UserDao userDao;

    @Test
    @Order(1)
    public void listItemsTest() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders.get("/item/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items.*", Matchers.hasSize(3)));
    }

    @Test
    @Order(2)
    public void viewItemTest() throws Exception {
        ItemModel itemModel = getItemModel();

        mvc
                .perform(MockMvcRequestBuilders.get("/item/view/" + itemModel.getUid()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.item.itemName", Matchers.equalToIgnoringCase(itemModel.getItemName())));
    }

    @Test
    @Order(3)
    public void buyItemTest() throws Exception {
        ItemModel itemModel = getItemModel();
        UserEntity userEntity = userDao.findDistinctFirstByEmail("alvin@gmail.com");

        mvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/item/buy")
                                .param("itemUid", itemModel.getUid())
                                .header("Authorization", "Bearer " + userEntity.getAccessToken()
                                )
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderUid", Matchers.notNullValue()));
    }

    @Test
    @Order(4)
    public void buyItemUnauthorizedTest() throws Exception {
        ItemModel itemModel = getItemModel();

        mvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/item/buy")
                                .param("itemUid", itemModel.getUid())
                                .header("Authorization", "Bearer " + RandomStringUtils.randomAlphabetic(20))
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    private ItemModel getItemModel() throws Exception {
        String itemsString = mvc.perform(MockMvcRequestBuilders.get("/item/list"))
                .andReturn().getResponse().getContentAsString();
        ItemListResponse itemList = new ObjectMapper().readValue(itemsString, ItemListResponse.class);
        return itemList.getItems().get(0);
    }
}
