package org.recap.repository;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.BibliographicEntity;
import org.recap.model.HoldingsEntity;
import org.recap.model.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by chenchulakshmig on 28/9/16.
 */
public class HoldingsDetailsRepositoryUT extends BaseTestCase {

    @Autowired
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    HoldingsDetailsRepository holdingsDetailsRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void saveAndFind() throws Exception {
        BibliographicEntity bibliographicEntity = saveSingleBibHoldingsMultipleItems();
        HoldingsEntity holdingsEntity = bibliographicEntity.getHoldingsEntities().get(0);

        List<ItemEntity> nonDeletedItemEntities = holdingsDetailsRepository.getNonDeletedItemEntities(holdingsEntity.getOwningInstitutionId(), holdingsEntity.getOwningInstitutionHoldingsId());
        Long nonDeletedItemsCount = holdingsDetailsRepository.getNonDeletedItemsCount(holdingsEntity.getOwningInstitutionId(), holdingsEntity.getOwningInstitutionHoldingsId());
        assertTrue(nonDeletedItemEntities.size() == nonDeletedItemsCount);
        assertTrue(nonDeletedItemEntities.size() == 1);
        assertEquals(nonDeletedItemEntities.get(0).getBarcode(), "12345656");

        List<ItemEntity> deletedItemEntities = holdingsDetailsRepository.getDeletedItemEntities(holdingsEntity.getOwningInstitutionId(), holdingsEntity.getOwningInstitutionHoldingsId());
        Long deletedItemsCount = holdingsDetailsRepository.getDeletedItemsCount(holdingsEntity.getOwningInstitutionId(), holdingsEntity.getOwningInstitutionHoldingsId());
        assertTrue(deletedItemEntities.size() == deletedItemsCount);
        assertTrue(deletedItemEntities.size() == 1);
        assertEquals(deletedItemEntities.get(0).getBarcode(), "123422");

        int updatedHoldingsCount = holdingsDetailsRepository.markHoldingsAsDeleted(Arrays.asList(holdingsEntity.getHoldingsId()));
        assertEquals(updatedHoldingsCount, 1);

        HoldingsEntity entity = holdingsDetailsRepository.findByHoldingsId(holdingsEntity.getHoldingsId());
        assertTrue(entity.isDeleted());

    }

    private BibliographicEntity saveSingleBibHoldingsMultipleItems() throws Exception {
        Random random = new Random();
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("mock Content".getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setCreatedBy("etl");
        bibliographicEntity.setLastUpdatedBy("etl");
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setOwningInstitutionBibId(String.valueOf(random.nextInt()));

        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setCreatedBy("etl");
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setLastUpdatedBy("etl");
        holdingsEntity.setOwningInstitutionId(1);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setCreatedDate(new Date());
        itemEntity1.setCreatedBy("etl");
        itemEntity1.setLastUpdatedDate(new Date());
        itemEntity1.setLastUpdatedBy("etl");
        itemEntity1.setCustomerCode("1");
        itemEntity1.setItemAvailabilityStatusId(1);
        itemEntity1.setOwningInstitutionItemId(String.valueOf(random.nextInt()));
        itemEntity1.setOwningInstitutionId(1);
        itemEntity1.setBarcode("12345656");
        itemEntity1.setCallNumber("x.12321");
        itemEntity1.setCollectionGroupId(1);
        itemEntity1.setCallNumberType("1");

        ItemEntity itemEntity2 = new ItemEntity();
        itemEntity2.setCreatedDate(new Date());
        itemEntity2.setCreatedBy("etl");
        itemEntity2.setLastUpdatedDate(new Date());
        itemEntity2.setLastUpdatedBy("etl");
        itemEntity2.setOwningInstitutionItemId(String.valueOf(random.nextInt()));
        itemEntity2.setOwningInstitutionId(1);
        itemEntity2.setCustomerCode("1");
        itemEntity2.setBarcode("123422");
        itemEntity2.setItemAvailabilityStatusId(1);
        itemEntity2.setCallNumber("x.12321");
        itemEntity2.setCollectionGroupId(1);
        itemEntity2.setCallNumberType("1");
        itemEntity2.setDeleted(true);

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity1, itemEntity2));
        holdingsEntity.setItemEntities(Arrays.asList(itemEntity1, itemEntity2));

        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);
        return savedBibliographicEntity;
    }
}