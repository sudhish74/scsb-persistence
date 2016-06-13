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
        BibliographictEntity bibliographictEntity = new BibliographictEntity();
        bibliographictEntity.setContent("Mock Bib Content");
        bibliographictEntity.setCreatedDate(new Date());
        bibliographictEntity.setOwningInstitutionBibId("1");
        bibliographictEntity.setOwningInstitutionId(1);
        BibliographictEntity savedbibliographictEntity = bibDetailsRespository.save(bibliographictEntity);
        Integer bibliographicId = savedbibliographictEntity.getBibliographicId();
        assertNotNull(bibliographicId);

        bibliographictEntity = bibDetailsRespository.findOne(bibliographicId);
        assertNotNull(bibliographictEntity);
    }

}
