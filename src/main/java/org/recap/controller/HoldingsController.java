package org.recap.controller;

import org.recap.model.HoldingsEntity;
import org.recap.repository.HoldingsDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(method = RequestMethod.GET)
    public HoldingsEntity findOne(Integer holdingsId) {
        return holdingsDetailsRepository.findOne(holdingsId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public HoldingsEntity create(@RequestBody HoldingsEntity holdingsEntity) {
        return holdingsDetailsRepository.save(holdingsEntity);
    }
}
