package org.recap.repository;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.BibliographicEntity;
import org.recap.model.HoldingsEntity;
import org.recap.model.ItemEntity;
import org.recap.model.ItemPK;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by chenchulakshmig on 21/9/16.
 */
public class ItemDetailsRepositoryUT extends BaseTestCase {

    @Autowired
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    ItemDetailsRepository itemDetailsRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void findByOwningInstitutionItemId() throws Exception {

    }

    @Test
    public void countByIsDeletedFalse() throws Exception {
        long countByIsDeletedFalse = itemDetailsRepository.countByIsDeletedFalse();
        List<ItemEntity> allByIsDeletedFalse = itemDetailsRepository.findAllByIsDeletedFalse();
        saveSingleBibHoldingsItem();

        long countByIsDeletedFalseAfterAdd = itemDetailsRepository.countByIsDeletedFalse();
        List<ItemEntity> allByIsDeletedFalseAfterAdd = (List<ItemEntity>) itemDetailsRepository.findAllByIsDeletedFalse();

        assertEquals(countByIsDeletedFalse + 1, countByIsDeletedFalseAfterAdd);
        assertEquals(allByIsDeletedFalse.size() + 1, allByIsDeletedFalseAfterAdd.size());
    }

    @Test
    public void countByIsDeletedTrue() throws Exception {
        long countByIsDeletedTrue = itemDetailsRepository.countByIsDeletedTrue();
        List<ItemEntity> allByIsDeletedTrue = itemDetailsRepository.findAllByIsDeletedTrue();

        BibliographicEntity bibliographicEntity = saveSingleBibHoldingsItem();
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        itemDetailsRepository.markItemAsDeleted(itemEntity.getItemId());

        long countByIsDeletedTrueAfterAdd = itemDetailsRepository.countByIsDeletedTrue();
        List<ItemEntity> allByIsDeletedTrueAfterAdd = (List<ItemEntity>) itemDetailsRepository.findAllByIsDeletedTrue();

        assertEquals(countByIsDeletedTrue + 1, countByIsDeletedTrueAfterAdd);
        assertEquals(allByIsDeletedTrue.size() + 1, allByIsDeletedTrueAfterAdd.size());
    }

    @Test
    public void findByBarcode() throws Exception {
        List<ItemEntity> itemEntities = itemDetailsRepository.findByBarcode("12316433");
        saveSingleBibHoldingsItem();
        List<ItemEntity> itemEntitiesAfterAdd = itemDetailsRepository.findByBarcode("12316433");
        assertEquals(itemEntities.size() + 1, itemEntitiesAfterAdd.size());
    }

    @Test
    public void findByBarcodeIn() throws Exception {
        List<ItemEntity> itemEntities = itemDetailsRepository.findByBarcodeIn(Arrays.asList("123698586", "123698"));
        saveSingleBibHoldingsMultipleItems();
        List<ItemEntity> itemEntitiesAfterAdd = itemDetailsRepository.findByBarcodeIn(Arrays.asList("123698586", "123698"));
        assertEquals(itemEntities.size() + 2, itemEntitiesAfterAdd.size());
    }

    @Test
    public void getItemStatusByBarcodeAndIsDeletedFalse() throws Exception {
        saveSingleBibHoldingsItem();
        String itemStatus = itemDetailsRepository.getItemStatusByBarcodeAndIsDeletedFalse("12316433");
        assertEquals(itemStatus, "Available");
    }

    @Test
    public void markItemAsDeleted() throws Exception {
        BibliographicEntity bibliographicEntity = saveSingleBibHoldingsItem();
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        ItemPK itemPK = new ItemPK(itemEntity.getOwningInstitutionId(), itemEntity.getOwningInstitutionItemId());
        ItemEntity savedItemEntity = itemDetailsRepository.findOne(itemPK);
        assertFalse(savedItemEntity.isDeleted());

        itemDetailsRepository.markItemAsDeleted(itemEntity.getItemId());

        ItemEntity savedItemEntityAfterDelete = itemDetailsRepository.findOne(itemPK);
        assertTrue(savedItemEntityAfterDelete.isDeleted());
    }

    private BibliographicEntity saveSingleBibHoldingsItem() {
        Random random = new Random();
        Date today = new Date();
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("mock Content".getBytes());
        bibliographicEntity.setCreatedDate(today);
        bibliographicEntity.setCreatedBy("etl");
        bibliographicEntity.setLastUpdatedBy("etl");
        bibliographicEntity.setLastUpdatedDate(today);
        bibliographicEntity.setOwningInstitutionId(1);
        String owningInstitutionBibId = String.valueOf(random.nextInt());
        bibliographicEntity.setOwningInstitutionBibId(owningInstitutionBibId);

        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(today);
        holdingsEntity.setCreatedBy("etl");
        holdingsEntity.setLastUpdatedDate(today);
        holdingsEntity.setLastUpdatedBy("etl");
        holdingsEntity.setOwningInstitutionId(1);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setCallNumberType("0");
        itemEntity.setCallNumber("callNum");
        itemEntity.setCopyNumber(1);
        itemEntity.setCreatedDate(today);
        itemEntity.setCreatedBy("etl");
        itemEntity.setLastUpdatedDate(today);
        itemEntity.setLastUpdatedBy("etl");
        itemEntity.setBarcode("12316433");
        itemEntity.setOwningInstitutionItemId(String.valueOf(random.nextInt()));
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCustomerCode("PA");
        itemEntity.setItemAvailabilityStatusId(1);

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));
        holdingsEntity.setItemEntities(Arrays.asList(itemEntity));

        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);
        return savedBibliographicEntity;
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
        itemEntity1.setBarcode("123698586");
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
        itemEntity2.setBarcode("123698");
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