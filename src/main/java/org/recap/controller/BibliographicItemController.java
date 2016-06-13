package org.recap.controller;

import org.recap.model.BibliographicItemEntity;
import org.recap.repository.BibliographicItemDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(method = RequestMethod.GET)
    public BibliographicItemEntity findOne(Integer bibliographicItemId) {
        return bibliographicItemDetailsRepository.findOne(bibliographicItemId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public BibliographicItemEntity create(@RequestBody BibliographicItemEntity bibliographicItemEntity) {
        return bibliographicItemDetailsRepository.save(bibliographicItemEntity);
    }
}
