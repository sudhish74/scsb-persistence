package org.recap.controller;

import org.recap.model.BibliographicEntity;
import org.recap.repository.BibliographicDetailsRepository;
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

    @RequestMapping(method = RequestMethod.GET, value = "/findOne")
    public BibliographicEntity findOne(Integer bibliographicId) {
        return bibliographicDetailsRepository.findOne(bibliographicId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByRangeOfIds")
    public List<BibliographicEntity> findByRangeOfIds(Integer fromId, Integer toId) {
        List<BibliographicEntity> bibliographicEntityList = new ArrayList<>();
        List<Integer> bibliographicIds = IntStream.rangeClosed(fromId, toId).boxed().collect(Collectors.toList());
        bibliographicEntityList.addAll((Collection<? extends BibliographicEntity>) bibliographicDetailsRepository.findAll(bibliographicIds));
        return bibliographicEntityList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAll")
    public List<BibliographicEntity> findAll() {
        List<BibliographicEntity> bibliographicEntityList = new ArrayList<>();
        bibliographicEntityList.addAll((Collection<? extends BibliographicEntity>) bibliographicDetailsRepository.findAll());
        return bibliographicEntityList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/count")
    public long count() {
        return bibliographicDetailsRepository.count();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public BibliographicEntity create(@RequestBody BibliographicEntity bibliographicEntity) {
        return bibliographicDetailsRepository.save(bibliographicEntity);
    }
}
