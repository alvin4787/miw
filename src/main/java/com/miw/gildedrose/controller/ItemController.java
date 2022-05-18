package com.miw.gildedrose.controller;

import com.miw.gildedrose.controller.response.ItemListResponse;
import com.miw.gildedrose.controller.response.ItemViewResponse;
import com.miw.gildedrose.controller.response.OrderResponse;
import com.miw.gildedrose.exception.CustomException;
import com.miw.gildedrose.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ItemListResponse getListOfItems() {
        return itemService.getAllItems();
    }

    @RequestMapping(value = "/view/{itemUid}", method = RequestMethod.GET)
    public ItemViewResponse viewItem(@PathVariable(name = "itemUid") String itemUid) throws CustomException {
        return itemService.viewItem(itemUid);
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public OrderResponse buyItem(@RequestParam(name = "itemUid") String itemUid) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return itemService.buyItem(itemUid, authentication.getName());
    }
}
