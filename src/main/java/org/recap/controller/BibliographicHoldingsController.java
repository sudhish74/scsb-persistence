package org.recap.controller;

import org.recap.model.BibliographicHoldingsEntity;
import org.recap.repository.BibliographicHoldingsDetailsRepository;
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
@RequestMapping("/bibliographicHoldings")
public class BibliographicHoldingsController {
    private final BibliographicHoldingsDetailsRepository bibliographicHoldingsDetailsRepository;

    @Autowired
    public BibliographicHoldingsController(BibliographicHoldingsDetailsRepository bibliographicHoldingsDetailsRepository) {
        this.bibliographicHoldingsDetailsRepository = bibliographicHoldingsDetailsRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findOne")
    public BibliographicHoldingsEntity findOne(Integer bibliographicHoldingsId) {
        return bibliographicHoldingsDetailsRepository.findOne(bibliographicHoldingsId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByRangeOfIds")
    public List<BibliographicHoldingsEntity> findByRangeOfIds(Integer fromId, Integer toId) {
        List<BibliographicHoldingsEntity> bibliographicHoldingsEntityList = new ArrayList<>();
        List<Integer> bibliographicHoldingsIds = IntStream.rangeClosed(fromId, toId).boxed().collect(Collectors.toList());
        bibliographicHoldingsEntityList.addAll((Collection<? extends BibliographicHoldingsEntity>) bibliographicHoldingsDetailsRepository.findAll(bibliographicHoldingsIds));
        return bibliographicHoldingsEntityList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAll")
    public List<BibliographicHoldingsEntity> findAll() {
        List<BibliographicHoldingsEntity> bibliographicHoldingsEntityList = new ArrayList<>();
        bibliographicHoldingsEntityList.addAll((Collection<? extends BibliographicHoldingsEntity>) bibliographicHoldingsDetailsRepository.findAll());
        return bibliographicHoldingsEntityList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/count")
    public long count() {
        return bibliographicHoldingsDetailsRepository.count();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public BibliographicHoldingsEntity create(@RequestBody BibliographicHoldingsEntity bibliographicHoldingsEntity) {
        return bibliographicHoldingsDetailsRepository.save(bibliographicHoldingsEntity);
    }
}
