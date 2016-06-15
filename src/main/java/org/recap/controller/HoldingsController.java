package org.recap.controller;

import org.recap.model.HoldingsEntity;
import org.recap.repository.HoldingsDetailsRepository;
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
@RequestMapping("/holdings")
public class HoldingsController {
    private final HoldingsDetailsRepository holdingsDetailsRepository;

    @Autowired
    public HoldingsController(HoldingsDetailsRepository holdingsDetailsRepository) {
        this.holdingsDetailsRepository = holdingsDetailsRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findOne")
    public HoldingsEntity findOne(Integer holdingsId) {
        return holdingsDetailsRepository.findOne(holdingsId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByRangeOfIds")
    public List<HoldingsEntity> findByRangeOfIds(Integer fromId, Integer toId) {
        List<HoldingsEntity> holdingsEntityList = new ArrayList<>();
        List<Integer> holdingsIds = IntStream.rangeClosed(fromId, toId).boxed().collect(Collectors.toList());
        holdingsEntityList.addAll((Collection<? extends HoldingsEntity>) holdingsDetailsRepository.findAll(holdingsIds));
        return holdingsEntityList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAll")
    public List<HoldingsEntity> findAll() {
        List<HoldingsEntity> holdingsEntityList = new ArrayList<>();
        holdingsEntityList.addAll((Collection<? extends HoldingsEntity>) holdingsDetailsRepository.findAll());
        return holdingsEntityList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/count")
    public long count() {
        return holdingsDetailsRepository.count();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public HoldingsEntity create(@RequestBody HoldingsEntity holdingsEntity) {
        return holdingsDetailsRepository.save(holdingsEntity);
    }
}
