package org.recap.controller;

import org.recap.model.BibliographicEntity;
import org.recap.repository.BibliographicDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pvsubrah on 6/11/16.
 */

@RestController
@RequestMapping("/bibliographic")
public class BibliographicController {
    private final BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    public BibliographicController(BibliographicDetailsRepository bibliographicDetailsRepository) {
        this.bibliographicDetailsRepository = bibliographicDetailsRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public BibliographicEntity findOne(Integer bibliographicId) {
        return bibliographicDetailsRepository.findOne(bibliographicId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public BibliographicEntity create(@RequestBody BibliographicEntity bibliographicEntity) {
        return bibliographicDetailsRepository.save(bibliographicEntity);
    }
}
