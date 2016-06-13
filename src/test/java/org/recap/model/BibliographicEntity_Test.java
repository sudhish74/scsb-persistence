package org.recap.model;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.repository.BibDetailsRespository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by pvsubrah on 6/10/16.
 */

public class BibliographicEntity_Test extends BaseTestCase {
    @Autowired
    BibDetailsRespository bibDetailsRespository;

    @Test
    public void saveBib() throws Exception {
        BibliographictEntity BibliographictEntity = new BibliographictEntity();
        BibliographictEntity.setContent("Mock Bib Content");
        BibliographictEntity.setCreatedDate(new Date());
        BibliographictEntity.setOwningInstitutionBibId("1");
        BibliographictEntity.setOwningInstitutionId(1);
        BibliographictEntity savedBibEntity = bibDetailsRespository.save(BibliographictEntity);
        Integer bibliographicId = savedBibEntity.getBibliographicId();
        assertNotNull(bibliographicId);

        BibliographictEntity bibliographictEntity = bibDetailsRespository.findOne(bibliographicId);

        assertNotNull(bibliographictEntity);
    }
}
