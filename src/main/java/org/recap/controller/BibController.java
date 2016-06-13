package org.recap.controller;

import org.recap.model.BibliographictEntity;
import org.recap.repository.BibDetailsRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pvsubrah on 6/11/16.
 */

@RestController
@RequestMapping("/bib")
public class BibController {
    private final BibDetailsRespository bibDetailsRespository;

    @Autowired
    public BibController(BibDetailsRespository bibDetailsRespository) {
        this.bibDetailsRespository = bibDetailsRespository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public BibliographictEntity findOne(Integer bibId) {
        return bibDetailsRespository.findOne(bibId);
    }
    @RequestMapping(method = RequestMethod.POST)
    public BibliographictEntity create(@RequestBody BibliographictEntity bibliographictEntity) {
        return bibDetailsRespository.save(bibliographictEntity);
    }
}
