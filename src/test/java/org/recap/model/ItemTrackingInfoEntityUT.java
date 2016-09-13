package org.recap.model;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.ItemTrackingInfoEntity;
import org.recap.repository.BibliographicDetailsRepository;
import org.recap.repository.HoldingsDetailsRepository;
import org.recap.repository.ItemTrackingInfoDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by hemalathas on 25/7/16.
 */
public class ItemTrackingInfoEntityUT extends BaseTestCase{

    @Autowired
    ItemTrackingInfoDetailsRepository itemTrackingInfoDetailsRepository;

    @Autowired
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    HoldingsDetailsRepository holdingsDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testItemTrackingInfo(){

        Random random = new Random();
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("mock Content".getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setCreatedBy("etl");
        bibliographicEntity.setLastUpdatedBy("etl");
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setOwningInstitutionId(1);
        String owningInstitutionBibId = String.valueOf(random.nextInt());
        bibliographicEntity.setOwningInstitutionBibId(owningInstitutionBibId);


        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setCreatedBy("etl");
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setLastUpdatedBy("etl");
        holdingsEntity.setOwningInstitutionId(1);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setCallNumberType("0");
        itemEntity.setCallNumber("callNum");
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("etl");
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setLastUpdatedBy("etl");
        itemEntity.setBarcode("1231");
        itemEntity.setOwningInstitutionItemId(".i1231");
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCustomerCode("PA");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setHoldingsEntity(holdingsEntity);

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));

        holdingsEntity.setItemEntities(Arrays.asList(itemEntity));

        BibliographicEntity savedEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedEntity);

        BibliographicPK bibliographicPK = new BibliographicPK();
        bibliographicPK.setOwningInstitutionId(1);
        bibliographicPK.setOwningInstitutionBibId(owningInstitutionBibId);
        BibliographicEntity byBibliographicPK = bibliographicDetailsRepository.findOne(bibliographicPK);

        assertNotNull(byBibliographicPK);
        assertNotNull(byBibliographicPK.getBibliographicId());
        assertNotNull(byBibliographicPK.getLastUpdatedBy());
        assertNotNull(byBibliographicPK.getCreatedBy());
        assertNotNull(byBibliographicPK.getHoldingsEntities().get(0).getItemEntities().get(0).getItemId());

        Date today = new Date();
        ItemTrackingInfoEntity itemTrackingInfoEntity = new ItemTrackingInfoEntity();
        itemTrackingInfoEntity.setTrackingStatusId(1);
        itemTrackingInfoEntity.setBillNumber("101");
        itemTrackingInfoEntity.setUpdatedDateTime(today);
        itemTrackingInfoEntity.setItemId(byBibliographicPK.getHoldingsEntities().get(0).getItemEntities().get(0).getItemId());
        ItemTrackingInfoEntity savedItemTrackingInfoEntity = itemTrackingInfoDetailsRepository.save(itemTrackingInfoEntity);
        assertNotNull(savedItemTrackingInfoEntity);
        assertNotNull(savedItemTrackingInfoEntity.getTrackingInfoId());
        assertEquals(savedItemTrackingInfoEntity.getItemId(),byBibliographicPK.getHoldingsEntities().get(0).getItemEntities().get(0).getItemId());
        assertTrue(savedItemTrackingInfoEntity.getTrackingStatusId() == 1);
        assertEquals(savedItemTrackingInfoEntity.getUpdatedDateTime(),today);
        assertEquals(savedItemTrackingInfoEntity.getBillNumber(),"101");
    }

}
