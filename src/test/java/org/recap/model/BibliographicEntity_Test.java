package org.recap.model;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.repository.BibliographicDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by pvsubrah on 6/10/16.
 */

public class BibliographicEntity_Test extends BaseTestCase {
    @Autowired
    BibliographicDetailsRepository bibDetailsRespository;

    @Test
    public void saveBibliographic() throws Exception {
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("Mock Bib Content");
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setOwningInstitutionBibId("1");
        bibliographicEntity.setOwningInstitutionId(1);
        BibliographicEntity savedbibliographictEntity = bibDetailsRespository.save(bibliographicEntity);
        Integer bibliographicId = savedbibliographictEntity.getBibliographicId();
        assertNotNull(bibliographicId);

        bibliographicEntity = bibDetailsRespository.findOne(bibliographicId);
        assertNotNull(bibliographicEntity);
    }

}
