package org.recap.controller;

import org.recap.model.BibliographicEntity;
import org.recap.model.BibliographicPK;
import org.recap.model.ItemEntity;
import org.recap.model.ItemPK;
import org.recap.repository.ItemDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @RequestMapping(method = RequestMethod.GET, value = "/findOne")
    public ItemEntity findOne(Integer owningInstitutionId,String owningInstitutionItemId) {
        ItemPK itemId = new ItemPK();
        itemId.setOwningInstitutionId(owningInstitutionId);
        itemId.setOwningInstitutionItemId(owningInstitutionItemId);
        return itemDetailsRepository.findOne(itemId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAll")
    public List<ItemEntity> findAll() {
        List<ItemEntity> itemEntityList = new ArrayList<>();
        itemEntityList.addAll((Collection<? extends ItemEntity>) itemDetailsRepository.findAll());
        return itemEntityList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/count")
    public long count() {
        return itemDetailsRepository.count();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ItemEntity create(@RequestBody ItemEntity itemEntity) {
        return itemDetailsRepository.save(itemEntity);
    }
}
