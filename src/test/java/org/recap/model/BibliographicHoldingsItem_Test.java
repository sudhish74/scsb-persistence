package org.recap.model;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by chenchulakshmig on 6/13/16.
 */
public class BibliographicHoldingsItem_Test extends BaseTestCase {
    @Autowired
    BibliographicDetailsRepository bibDetailsRespository;

    @Autowired
    HoldingsDetailsRepository holdingsDetailsRepository;

    @Autowired
    BibliographicHoldingsDetailsRepository bibliographicHoldingsDetailsRepository;

    @Autowired
    ItemDetailsRepository itemDetailsRepository;

    @Autowired
    BibliographicItemDetailsRepository bibliographicItemDetailsRepository;

    @Test
    public void saveBibliographicHoldings() throws Exception {
        BibliographictEntity bibliographictEntity = new BibliographictEntity();
        bibliographictEntity.setContent("Mock Bib Content");
        bibliographictEntity.setCreatedDate(new Date());
        bibliographictEntity.setOwningInstitutionBibId("1");
        bibliographictEntity.setOwningInstitutionId(1);
        BibliographictEntity savedBibEntity = bibDetailsRespository.save(bibliographictEntity);
        Integer bibliographicId = savedBibEntity.getBibliographicId();
        assertNotNull(bibliographicId);
        bibliographictEntity = bibDetailsRespository.findOne(bibliographicId);
        assertNotNull(bibliographictEntity);

        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent("Holdings Bib Content");
        holdingsEntity.setBibliographicId(bibliographicId);
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setOwningInstitutionHoldingsId("11");
        HoldingsEntity savedHoldingsEntity = holdingsDetailsRepository.save(holdingsEntity);
        Integer holdingsId = savedHoldingsEntity.getHoldingsId();
        assertNotNull(holdingsId);
        holdingsEntity = holdingsDetailsRepository.findOne(holdingsId);
        assertNotNull(holdingsEntity);

        BibliographicHoldingsEntity bibliographicHoldingsEntity = new BibliographicHoldingsEntity();
        bibliographicHoldingsEntity.setBibliographicId(bibliographicId);
        bibliographicHoldingsEntity.setHoldingsId(holdingsId);
        BibliographicHoldingsEntity savedBibliographicHoldingsEntity = bibliographicHoldingsDetailsRepository.save(bibliographicHoldingsEntity);
        Integer bibliographicHoldingsId = savedBibliographicHoldingsEntity.getBibliographicHoldingsId();
        assertNotNull(bibliographicHoldingsId);
        bibliographicHoldingsEntity = bibliographicHoldingsDetailsRepository.findOne(bibliographicHoldingsId);
        assertNotNull(bibliographicHoldingsEntity);

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setBarcode("111");
        itemEntity.setCustomerCode("private");
        itemEntity.setHoldingsId(holdingsId);
        itemEntity.setCallNumber("x");
        itemEntity.setCallNumberType("1");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setCopyNumber(1);
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCreatedDate(new Date());
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setBibliographicId(bibliographicId);
        itemEntity.setUseRestrictions("use");
        itemEntity.setVolumePartYear("xxx");
        itemEntity.setOwningInstitutionItemId("111");
        itemEntity.setNotesId(1);
        ItemEntity savedItemEntity = itemDetailsRepository.save(itemEntity);
        Integer itemId = savedItemEntity.getItemId();
        assertNotNull(itemId);
        itemEntity = itemDetailsRepository.findOne(itemId);
        assertNotNull(itemEntity);

        BibliographicItemEntity bibliographicItemEntity = new BibliographicItemEntity();
        bibliographicItemEntity.setBibliographicId(bibliographicId);
        bibliographicItemEntity.setItemId(itemId);
        BibliographicItemEntity savedBibliographicItemEntity = bibliographicItemDetailsRepository.save(bibliographicItemEntity);
        Integer bibliographicItemId = savedBibliographicItemEntity.getBibliographicItemId();
        assertNotNull(bibliographicItemId);
        bibliographicItemEntity = bibliographicItemDetailsRepository.findOne(bibliographicItemId);
        assertNotNull(bibliographicItemEntity);
    }
}
