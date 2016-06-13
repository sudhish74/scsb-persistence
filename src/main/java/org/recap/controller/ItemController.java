package org.recap.controller;

import org.recap.model.ItemEntity;
import org.recap.repository.ItemDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenchulakshmig on 6/13/16.
 */
@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemDetailsRepository itemDetailsRepository;

    @Autowired
    public ItemController(ItemDetailsRepository itemDetailsRepository) {
        this.itemDetailsRepository = itemDetailsRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ItemEntity findOne(Integer itemId) {
        return itemDetailsRepository.findOne(itemId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ItemEntity create(@RequestBody ItemEntity itemEntity) {
        return itemDetailsRepository.save(itemEntity);
    }
}
