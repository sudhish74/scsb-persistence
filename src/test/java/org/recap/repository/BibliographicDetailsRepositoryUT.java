package org.recap.repository;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.controller.BaseControllerUT;
import org.recap.model.BibliographicEntity;
import org.recap.model.HoldingsEntity;
import org.recap.model.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by chenchulakshmig on 28/9/16.
 */
public class BibliographicDetailsRepositoryUT extends BaseControllerUT {

    @Autowired
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void saveAndFind() throws Exception {
        BibliographicEntity bibliographicEntity = saveSingleBibMultipleHoldingsItems();

        List<ItemEntity> nonDeletedItemEntities = bibliographicDetailsRepository.getNonDeletedItemEntities(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId());
        Long nonDeletedItemsCount = bibliographicDetailsRepository.getNonDeletedItemsCount(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId());
        assertTrue(nonDeletedItemEntities.size() == nonDeletedItemsCount);
        assertTrue(nonDeletedItemEntities.size() == 1);
        assertEquals(nonDeletedItemEntities.get(0).getBarcode(), "123456");

        List<ItemEntity> deletedItemEntities = bibliographicDetailsRepository.getDeletedItemEntities(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId());
        Long deletedItemEntitiesCount = bibliographicDetailsRepository.getDeletedItemsCount(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId());
        assertTrue(deletedItemEntities.size() == deletedItemEntitiesCount);
        assertTrue(deletedItemEntities.size() == 1);
        assertEquals(deletedItemEntities.get(0).getBarcode(), "1234");

        List<HoldingsEntity> nonDeletedHoldingsEntities = bibliographicDetailsRepository.getNonDeletedHoldingsEntities(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId());
        Long nonDeletedHoldingsCount = bibliographicDetailsRepository.getNonDeletedHoldingsCount(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId());
        assertTrue(nonDeletedHoldingsEntities.size() == nonDeletedHoldingsCount);
        assertTrue(nonDeletedHoldingsEntities.size() == 1);

        List<HoldingsEntity> deletedHoldingsEntities = bibliographicDetailsRepository.getDeletedHoldingsEntities(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId());
        Long deletedHoldingsCount = bibliographicDetailsRepository.getDeletedHoldingsCount(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId());
        assertTrue(deletedHoldingsEntities.size() == deletedHoldingsCount);
        assertTrue(deletedHoldingsEntities.size() == 1);

        int updatedBibCount = bibliographicDetailsRepository.markBibsAsDeleted(Arrays.asList(bibliographicEntity.getBibliographicId()));
        assertEquals(updatedBibCount, 1);

        BibliographicEntity entity = bibliographicDetailsRepository.findByOwningInstitutionIdAndOwningInstitutionBibId(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId());
        assertTrue(entity.isDeleted());

        MvcResult mvcResult = this.mockMvc.perform(get("/bibliographic/search/findByOwningInstitutionIdAndOwningInstitutionBibId")
                .param("owningInstitutionId", String.valueOf(bibliographicEntity.getOwningInstitutionId()))
                .param("owningInstitutionBibId", bibliographicEntity.getOwningInstitutionBibId()))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);
    }

    private BibliographicEntity saveSingleBibMultipleHoldingsItems() throws Exception {
        Random random = new Random();
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("mock Content".getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setCreatedBy("etl");
        bibliographicEntity.setLastUpdatedBy("etl");
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setOwningInstitutionBibId(String.valueOf(random.nextInt()));

        HoldingsEntity holdingsEntity1 = new HoldingsEntity();
        holdingsEntity1.setContent("mock holdings".getBytes());
        holdingsEntity1.setCreatedDate(new Date());
        holdingsEntity1.setCreatedBy("etl");
        holdingsEntity1.setLastUpdatedDate(new Date());
        holdingsEntity1.setLastUpdatedBy("etl");
        holdingsEntity1.setOwningInstitutionId(1);
        holdingsEntity1.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        HoldingsEntity holdingsEntity2 = new HoldingsEntity();
        holdingsEntity2.setContent("mock holdings".getBytes());
        holdingsEntity2.setCreatedDate(new Date());
        holdingsEntity2.setCreatedBy("etl");
        holdingsEntity2.setLastUpdatedDate(new Date());
        holdingsEntity2.setLastUpdatedBy("etl");
        holdingsEntity2.setOwningInstitutionId(1);
        holdingsEntity2.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));
        holdingsEntity2.setDeleted(true);

        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setCreatedDate(new Date());
        itemEntity1.setCreatedBy("etl");
        itemEntity1.setLastUpdatedDate(new Date());
        itemEntity1.setLastUpdatedBy("etl");
        itemEntity1.setCustomerCode("1");
        itemEntity1.setItemAvailabilityStatusId(1);
        itemEntity1.setOwningInstitutionItemId(String.valueOf(random.nextInt()));
        itemEntity1.setOwningInstitutionId(1);
        itemEntity1.setBarcode("123456");
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
        itemEntity2.setBarcode("1234");
        itemEntity2.setItemAvailabilityStatusId(1);
        itemEntity2.setCallNumber("x.12321");
        itemEntity2.setCollectionGroupId(1);
        itemEntity2.setCallNumberType("1");
        itemEntity2.setDeleted(true);

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity1, holdingsEntity2));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity1, itemEntity2));
        holdingsEntity1.setItemEntities(Arrays.asList(itemEntity1));
        holdingsEntity2.setItemEntities(Arrays.asList(itemEntity2));

        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);
        return savedBibliographicEntity;
    }
}