package org.recap.model;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.repository.BibliographicDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StopWatch;

import java.util.*;

import static org.junit.Assert.assertNotNull;

/**
 * Created by pvsubrah on 6/10/16.
 */

public class BibliographicEntity_Test extends BaseTestCase {
    @Autowired
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @Test
    public void saveBibliographic() throws Exception {
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("Mock Bib Content");
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setOwningInstitutionBibId("777");
        bibliographicEntity.setOwningInstitutionId(1);
        BibliographicEntity savedbibliographictEntity = bibliographicDetailsRepository.save(bibliographicEntity);
        Integer bibliographicId = savedbibliographictEntity.getBibliographicId();
        assertNotNull(bibliographicId);

        bibliographicEntity = bibliographicDetailsRepository.findOne(bibliographicId);
        assertNotNull(bibliographicEntity);
    }

    @Test
    public void findAll() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<BibliographicEntity> bibliographicEntityList = new ArrayList<>();
        bibliographicEntityList.addAll((Collection<? extends BibliographicEntity>) bibliographicDetailsRepository.findAll());
        stopWatch.stop();
        System.out.println("Total time for " + bibliographicEntityList.size() + " - bibs : " + stopWatch.getTotalTimeSeconds() + "ms");
    }


    @Test
    public void findAllByPage() throws Exception {
        Page<BibliographicEntity> bibliographicEntityPage = bibliographicDetailsRepository.findAll(new PageRequest(0, 10));

        Iterator<BibliographicEntity> iterator = bibliographicEntityPage.iterator();
        while(iterator.hasNext()){
            BibliographicEntity bibEntity = iterator.next();
            System.out.println(bibEntity.getBibliographicId());
        }

        Page<BibliographicEntity> bibliographicEntityPage1 = bibliographicDetailsRepository.findAll(new PageRequest(1, 10));

        Iterator<BibliographicEntity> iterator1 = bibliographicEntityPage1.iterator();
        while(iterator1.hasNext()){
            BibliographicEntity bibEntity = iterator1.next();
            System.out.println(bibEntity.getBibliographicId());
        }
    }

}
