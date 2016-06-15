package org.recap.controller;

import org.recap.model.BibliographicItemEntity;
import org.recap.repository.BibliographicItemDetailsRepository;
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
@RequestMapping("/bibliographicItem")
public class BibliographicItemController {
    private final BibliographicItemDetailsRepository bibliographicItemDetailsRepository;

    @Autowired
    public BibliographicItemController(BibliographicItemDetailsRepository bibliographicItemDetailsRepository) {
        this.bibliographicItemDetailsRepository = bibliographicItemDetailsRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findOne")
    public BibliographicItemEntity findOne(Integer bibliographicItemId) {
        return bibliographicItemDetailsRepository.findOne(bibliographicItemId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByRangeOfIds")
    public List<BibliographicItemEntity> findByRangeOfIds(Integer fromId, Integer toId) {
        List<BibliographicItemEntity> bibliographicItemEntityList = new ArrayList<>();
        List<Integer> bibliographicItemIds = IntStream.rangeClosed(fromId, toId).boxed().collect(Collectors.toList());
        bibliographicItemEntityList.addAll((Collection<? extends BibliographicItemEntity>) bibliographicItemDetailsRepository.findAll(bibliographicItemIds));
        return bibliographicItemEntityList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAll")
    public List<BibliographicItemEntity> findAll() {
        List<BibliographicItemEntity> bibliographicItemEntityList = new ArrayList<>();
        bibliographicItemEntityList.addAll((Collection<? extends BibliographicItemEntity>) bibliographicItemDetailsRepository.findAll());
        return bibliographicItemEntityList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/count")
    public long count() {
        return bibliographicItemDetailsRepository.count();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public BibliographicItemEntity create(@RequestBody BibliographicItemEntity bibliographicItemEntity) {
        return bibliographicItemDetailsRepository.save(bibliographicItemEntity);
    }
}
