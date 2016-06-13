package org.recap.controller;

import org.recap.model.BibliographicHoldingsEntity;
import org.recap.repository.BibliographicHoldingsDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(method = RequestMethod.GET)
    public BibliographicHoldingsEntity findOne(Integer bibliographicHoldingsId) {
        return bibliographicHoldingsDetailsRepository.findOne(bibliographicHoldingsId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public BibliographicHoldingsEntity create(@RequestBody BibliographicHoldingsEntity bibliographicHoldingsEntity) {
        return bibliographicHoldingsDetailsRepository.save(bibliographicHoldingsEntity);
    }
}
