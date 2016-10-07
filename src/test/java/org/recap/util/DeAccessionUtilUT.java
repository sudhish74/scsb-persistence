package org.recap.util;

import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.ReCAPConstants;
import org.recap.model.*;
import org.recap.repository.BibliographicDetailsRepository;
import org.recap.repository.ItemDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by chenchulakshmig on 29/9/16.
 */
public class DeAccessionUtilUT extends BaseTestCase {

    @Autowired
    DeAccessionUtil deAccessionUtil;

    @Autowired
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    ItemDetailsRepository itemDetailsRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void markAsDeleteSingleBibHoldingsItem() throws Exception {
        saveSingleBibHoldingsItem();

        JSONObject jsonObject = new JSONObject("{\"itemBarcode\": \"1231\"}");
        String itemBarcode = jsonObject.getString("itemBarcode");

        List<DeAccessionDBResponseEntity> deAccessionDBResponseEntities = deAccessionUtil.deAccessionItemsInDB(Arrays.asList(itemBarcode));
        DeAccessionDBResponseEntity deAccessionDBResponseEntity = deAccessionDBResponseEntities.get(0);
        assertEquals(deAccessionDBResponseEntity.getStatus(), ReCAPConstants.SUCCESS);

        List<ItemEntity> itemEntities = itemDetailsRepository.findByBarcode(itemBarcode);
        assertNotNull(itemEntities);
        assertTrue(itemEntities.size() == 1);
        ItemEntity itemEntity = itemEntities.get(0);
        assertTrue(itemEntity.isDeleted());
        assertEquals(deAccessionDBResponseEntity.getItemId(), itemEntity.getItemId());

        List<HoldingsEntity> holdingsEntities = itemEntity.getHoldingsEntities();
        assertNotNull(holdingsEntities);
        assertTrue(holdingsEntities.size() == 1);
        assertTrue(holdingsEntities.get(0).isDeleted());
        assertEquals(deAccessionDBResponseEntity.getHoldingIds(), Arrays.asList(holdingsEntities.get(0).getHoldingsId()));

        List<BibliographicEntity> bibliographicEntities = itemEntity.getBibliographicEntities();
        assertNotNull(bibliographicEntities);
        assertTrue(bibliographicEntities.size() == 1);
        assertTrue(bibliographicEntities.get(0).isDeleted());
        assertEquals(deAccessionDBResponseEntity.getBibliographicIds(), Arrays.asList(bibliographicEntities.get(0).getBibliographicId()));

        List<ReportEntity> reportEntities = deAccessionUtil.processAndSave(Arrays.asList(deAccessionDBResponseEntity));
        assertNotNull(reportEntities);
        assertEquals(reportEntities.size(), 1);
    }

    @Test
    public void markAsDeleteSingleBibHoldingsMultipleItem() throws Exception {
        saveSingleBibHoldingsMultipleItem();

        JSONObject jsonObject = new JSONObject("{\"itemBarcode\": \"123\"}");
        String itemBarcode = jsonObject.getString("itemBarcode");

        List<DeAccessionDBResponseEntity> deAccessionDBResponseEntities = deAccessionUtil.deAccessionItemsInDB(Arrays.asList(itemBarcode));
        DeAccessionDBResponseEntity deAccessionDBResponseEntity = deAccessionDBResponseEntities.get(0);
        assertEquals(deAccessionDBResponseEntity.getStatus(), ReCAPConstants.SUCCESS);

        List<ItemEntity> itemEntities = itemDetailsRepository.findByBarcode(itemBarcode);
        assertNotNull(itemEntities);
        assertTrue(itemEntities.size() == 1);
        ItemEntity itemEntity = itemEntities.get(0);
        assertTrue(itemEntity.isDeleted());
        assertEquals(deAccessionDBResponseEntity.getItemId(), itemEntity.getItemId());

        List<HoldingsEntity> holdingsEntities = itemEntity.getHoldingsEntities();
        assertNotNull(holdingsEntities);
        assertTrue(holdingsEntities.size() == 1);
        assertFalse(holdingsEntities.get(0).isDeleted());
        assertEquals(deAccessionDBResponseEntity.getHoldingIds(), Arrays.asList());

        List<BibliographicEntity> bibliographicEntities = itemEntity.getBibliographicEntities();
        assertNotNull(bibliographicEntities);
        assertTrue(bibliographicEntities.size() == 1);
        assertFalse(bibliographicEntities.get(0).isDeleted());
        assertEquals(deAccessionDBResponseEntity.getBibliographicIds(), Arrays.asList());

        List<ReportEntity> reportEntities = deAccessionUtil.processAndSave(Arrays.asList(deAccessionDBResponseEntity));
        assertNotNull(reportEntities);
        assertEquals(reportEntities.size(), 1);
    }

    @Test
    public void markAsDeleteSingleBibMultipleHoldingsItems() throws Exception {
        saveSingleBibMultipleHoldingsItems();

        JSONObject jsonObject = new JSONObject("{\"itemBarcode\": \"123\"}");
        String itemBarcode = jsonObject.getString("itemBarcode");

        List<DeAccessionDBResponseEntity> deAccessionDBResponseEntities = deAccessionUtil.deAccessionItemsInDB(Arrays.asList(itemBarcode));
        DeAccessionDBResponseEntity deAccessionDBResponseEntity = deAccessionDBResponseEntities.get(0);
        assertEquals(deAccessionDBResponseEntity.getStatus(), ReCAPConstants.SUCCESS);

        List<ItemEntity> itemEntities = itemDetailsRepository.findByBarcode(itemBarcode);
        assertNotNull(itemEntities);
        assertTrue(itemEntities.size() == 1);
        ItemEntity itemEntity = itemEntities.get(0);
        assertTrue(itemEntity.isDeleted());
        assertEquals(deAccessionDBResponseEntity.getItemId(), itemEntity.getItemId());

        List<HoldingsEntity> holdingsEntities = itemEntity.getHoldingsEntities();
        assertNotNull(holdingsEntities);
        assertTrue(holdingsEntities.size() == 1);
        assertTrue(holdingsEntities.get(0).isDeleted());
        assertEquals(deAccessionDBResponseEntity.getHoldingIds(), Arrays.asList(holdingsEntities.get(0).getHoldingsId()));

        List<BibliographicEntity> bibliographicEntities = itemEntity.getBibliographicEntities();
        assertNotNull(bibliographicEntities);
        assertTrue(bibliographicEntities.size() == 1);
        assertFalse(bibliographicEntities.get(0).isDeleted());
        assertEquals(deAccessionDBResponseEntity.getBibliographicIds(), Arrays.asList());

        List<ReportEntity> reportEntities = deAccessionUtil.processAndSave(Arrays.asList(deAccessionDBResponseEntity));
        assertNotNull(reportEntities);
        assertEquals(reportEntities.size(), 1);
    }

    @Test
    public void markAsDeleteBoundWithItems() throws Exception {
        boundWithItemsSingleHolding();

        JSONObject jsonObject = new JSONObject("{\"itemBarcode\": \"123\"}");
        String itemBarcode = jsonObject.getString("itemBarcode");

        List<DeAccessionDBResponseEntity> deAccessionDBResponseEntities = deAccessionUtil.deAccessionItemsInDB(Arrays.asList(itemBarcode));
        DeAccessionDBResponseEntity deAccessionDBResponseEntity = deAccessionDBResponseEntities.get(0);
        assertEquals(deAccessionDBResponseEntity.getStatus(), ReCAPConstants.SUCCESS);

        List<ItemEntity> itemEntities = itemDetailsRepository.findByBarcode(itemBarcode);
        assertNotNull(itemEntities);
        assertTrue(itemEntities.size() == 1);
        ItemEntity itemEntity = itemEntities.get(0);
        assertTrue(itemEntity.isDeleted());
        assertEquals(deAccessionDBResponseEntity.getItemId(), itemEntity.getItemId());

        List<HoldingsEntity> holdingsEntities = itemEntity.getHoldingsEntities();
        assertNotNull(holdingsEntities);
        assertTrue(holdingsEntities.size() == 1);
        assertTrue(holdingsEntities.get(0).isDeleted());
        assertEquals(deAccessionDBResponseEntity.getHoldingIds(), Arrays.asList(holdingsEntities.get(0).getHoldingsId()));

        List<BibliographicEntity> bibliographicEntities = itemEntity.getBibliographicEntities();
        assertNotNull(bibliographicEntities);
        assertTrue(bibliographicEntities.size() == 2);
        assertTrue(bibliographicEntities.get(0).isDeleted());
        assertTrue(bibliographicEntities.get(1).isDeleted());
        assertEquals(deAccessionDBResponseEntity.getBibliographicIds(), Arrays.asList(bibliographicEntities.get(0).getBibliographicId(), bibliographicEntities.get(1).getBibliographicId()));

        List<ReportEntity> reportEntities = deAccessionUtil.processAndSave(Arrays.asList(deAccessionDBResponseEntity));
        assertNotNull(reportEntities);
        assertEquals(reportEntities.size(), 2);
    }

    @Test
    public void markAsDeleteBoundWithItemsMultipleHolding() throws Exception {
        boundWithItemsMultipleHolding();

        JSONObject jsonObject = new JSONObject("{\"itemBarcode\": \"123\"}");
        String itemBarcode = jsonObject.getString("itemBarcode");

        List<DeAccessionDBResponseEntity> deAccessionDBResponseEntities = deAccessionUtil.deAccessionItemsInDB(Arrays.asList(itemBarcode));
        DeAccessionDBResponseEntity deAccessionDBResponseEntity = deAccessionDBResponseEntities.get(0);
        assertEquals(deAccessionDBResponseEntity.getStatus(), ReCAPConstants.SUCCESS);

        List<ItemEntity> itemEntities = itemDetailsRepository.findByBarcode(itemBarcode);
        assertNotNull(itemEntities);
        assertTrue(itemEntities.size() == 1);
        ItemEntity itemEntity = itemEntities.get(0);
        assertTrue(itemEntity.isDeleted());
        assertEquals(deAccessionDBResponseEntity.getItemId(), itemEntity.getItemId());

        List<HoldingsEntity> holdingsEntities = itemEntity.getHoldingsEntities();
        assertNotNull(holdingsEntities);
        assertTrue(holdingsEntities.size() == 2);
        assertTrue(holdingsEntities.get(0).isDeleted());
        assertTrue(holdingsEntities.get(1).isDeleted());
        assertEquals(deAccessionDBResponseEntity.getHoldingIds(), Arrays.asList(holdingsEntities.get(0).getHoldingsId(), holdingsEntities.get(1).getHoldingsId()));

        List<BibliographicEntity> bibliographicEntities = itemEntity.getBibliographicEntities();
        assertNotNull(bibliographicEntities);
        assertTrue(bibliographicEntities.size() == 2);
        assertTrue(bibliographicEntities.get(0).isDeleted());
        assertTrue(bibliographicEntities.get(1).isDeleted());
        assertEquals(deAccessionDBResponseEntity.getBibliographicIds(), Arrays.asList(bibliographicEntities.get(0).getBibliographicId(), bibliographicEntities.get(1).getBibliographicId()));

        List<ReportEntity> reportEntities = deAccessionUtil.processAndSave(Arrays.asList(deAccessionDBResponseEntity));
        assertNotNull(reportEntities);
        assertEquals(reportEntities.size(), 2);
    }

    @Test
    public void testByValidAndInvalidBarcodes() throws Exception {
        saveSingleBibHoldingsItem();

        List<DeAccessionDBResponseEntity> deAccessionDBResponseEntities = deAccessionUtil.deAccessionItemsInDB(Arrays.asList("123", "1231"));
        assertNotNull(deAccessionDBResponseEntities);
        assertEquals(deAccessionDBResponseEntities.size(), 2);
    }

    @Test
    public void markAsDeleteByInvalidBarcode() throws Exception {
        JSONObject jsonObject = new JSONObject("{\"itemBarcode\": \"123\"}");
        String itemBarcode = jsonObject.getString("itemBarcode");

        List<DeAccessionDBResponseEntity> deAccessionDBResponseEntities = deAccessionUtil.deAccessionItemsInDB(Arrays.asList(itemBarcode));
        DeAccessionDBResponseEntity deAccessionDBResponseEntity = deAccessionDBResponseEntities.get(0);
        assertEquals(deAccessionDBResponseEntity.getStatus(), ReCAPConstants.FAILURE);
        assertEquals(deAccessionDBResponseEntity.getReasonForFailure(), "Item Barcode doesn't exist in SCSB database.");

        List<ReportEntity> reportEntities = deAccessionUtil.processAndSave(Arrays.asList(deAccessionDBResponseEntity));
        assertNotNull(reportEntities);
        assertEquals(reportEntities.size(), 1);
    }

    @Test
    public void markAsDeleteByBarcode() throws Exception {
        saveSingleBibHoldingsItem();

        JSONObject jsonObject = new JSONObject("{\"itemBarcode\": \"1231\"}");
        String itemBarcode = jsonObject.getString("itemBarcode");

        List<ItemEntity> itemEntityList = itemDetailsRepository.findByBarcode(itemBarcode);
        assertNotNull(itemEntityList);
        assertTrue(itemEntityList.size() == 1);
        ItemEntity itemEntity = itemEntityList.get(0);
        itemDetailsRepository.markItemAsDeleted(itemEntity.getItemId());

        List<DeAccessionDBResponseEntity> deAccessionDBResponseEntities = deAccessionUtil.deAccessionItemsInDB(Arrays.asList(itemBarcode));
        DeAccessionDBResponseEntity deAccessionDBResponseEntity = deAccessionDBResponseEntities.get(0);
        assertEquals(deAccessionDBResponseEntity.getStatus(), ReCAPConstants.FAILURE);
        assertEquals(deAccessionDBResponseEntity.getReasonForFailure(), "The requested item already been DeAccessioned.");

        List<ReportEntity> reportEntities = deAccessionUtil.processAndSave(Arrays.asList(deAccessionDBResponseEntity));
        assertNotNull(reportEntities);
        assertEquals(reportEntities.size(), 1);
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
        itemEntity.setBarcode("1231");
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

    public void saveSingleBibHoldingsMultipleItem() throws Exception {
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
        itemEntity1.setBarcode("123");
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

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity1, itemEntity2));
        holdingsEntity.setItemEntities(Arrays.asList(itemEntity1, itemEntity2));

        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);
    }

    private void saveSingleBibMultipleHoldingsItems() throws Exception {
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

        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setCreatedDate(new Date());
        itemEntity1.setCreatedBy("etl");
        itemEntity1.setLastUpdatedDate(new Date());
        itemEntity1.setLastUpdatedBy("etl");
        itemEntity1.setCustomerCode("1");
        itemEntity1.setItemAvailabilityStatusId(1);
        itemEntity1.setOwningInstitutionItemId(String.valueOf(random.nextInt()));
        itemEntity1.setOwningInstitutionId(1);
        itemEntity1.setBarcode("123");
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

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity1, holdingsEntity2));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity1, itemEntity2));
        holdingsEntity1.setItemEntities(Arrays.asList(itemEntity1));
        holdingsEntity2.setItemEntities(Arrays.asList(itemEntity2));

        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);
    }

    public void boundWithItemsSingleHolding() throws Exception {
        Random random = new Random();
        BibliographicEntity bibliographicEntity1 = new BibliographicEntity();
        bibliographicEntity1.setContent("mock Content".getBytes());
        bibliographicEntity1.setCreatedDate(new Date());
        bibliographicEntity1.setCreatedBy("etl");
        bibliographicEntity1.setLastUpdatedBy("etl");
        bibliographicEntity1.setLastUpdatedDate(new Date());
        bibliographicEntity1.setOwningInstitutionId(1);
        bibliographicEntity1.setOwningInstitutionBibId("10001");

        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setCreatedBy("etl");
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setLastUpdatedBy("etl");
        holdingsEntity.setOwningInstitutionId(1);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("etl");
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setLastUpdatedBy("etl");
        itemEntity.setCustomerCode("1");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setOwningInstitutionItemId("101");
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setBarcode("123");
        itemEntity.setCallNumber("x.12321");
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCallNumberType("1");

        BibliographicEntity bibliographicEntity2 = new BibliographicEntity();
        bibliographicEntity2.setContent("mock Content".getBytes());
        bibliographicEntity2.setCreatedDate(new Date());
        bibliographicEntity2.setCreatedBy("etl");
        bibliographicEntity2.setLastUpdatedBy("etl");
        bibliographicEntity2.setLastUpdatedDate(new Date());
        bibliographicEntity2.setOwningInstitutionId(1);
        bibliographicEntity2.setOwningInstitutionBibId("10002");

        bibliographicEntity1.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity1.setItemEntities(Arrays.asList(itemEntity));

        bibliographicEntity2.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity2.setItemEntities(Arrays.asList(itemEntity));
        holdingsEntity.setItemEntities(Arrays.asList(itemEntity));

        BibliographicEntity savedEntity1 = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity1);
        entityManager.refresh(savedEntity1);
        BibliographicEntity savedEntity2 = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity2);
        entityManager.refresh(savedEntity2);
    }

    public void boundWithItemsMultipleHolding() throws Exception {
        Random random = new Random();
        BibliographicEntity bibliographicEntity1 = new BibliographicEntity();
        bibliographicEntity1.setContent("mock Content".getBytes());
        bibliographicEntity1.setCreatedDate(new Date());
        bibliographicEntity1.setCreatedBy("etl");
        bibliographicEntity1.setLastUpdatedBy("etl");
        bibliographicEntity1.setLastUpdatedDate(new Date());
        bibliographicEntity1.setOwningInstitutionId(1);
        bibliographicEntity1.setOwningInstitutionBibId("10001");

        HoldingsEntity holdingsEntity1 = new HoldingsEntity();
        holdingsEntity1.setContent("mock holdings".getBytes());
        holdingsEntity1.setCreatedDate(new Date());
        holdingsEntity1.setCreatedBy("etl");
        holdingsEntity1.setLastUpdatedDate(new Date());
        holdingsEntity1.setLastUpdatedBy("etl");
        holdingsEntity1.setOwningInstitutionId(1);
        holdingsEntity1.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("etl");
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setLastUpdatedBy("etl");
        itemEntity.setCustomerCode("1");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setOwningInstitutionItemId("101");
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setBarcode("123");
        itemEntity.setCallNumber("x.12321");
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCallNumberType("1");

        BibliographicEntity bibliographicEntity2 = new BibliographicEntity();
        bibliographicEntity2.setContent("mock Content".getBytes());
        bibliographicEntity2.setCreatedDate(new Date());
        bibliographicEntity2.setCreatedBy("etl");
        bibliographicEntity2.setLastUpdatedBy("etl");
        bibliographicEntity2.setLastUpdatedDate(new Date());
        bibliographicEntity2.setOwningInstitutionId(1);
        bibliographicEntity2.setOwningInstitutionBibId("10002");

        HoldingsEntity holdingsEntity2 = new HoldingsEntity();
        holdingsEntity2.setContent("mock holdings".getBytes());
        holdingsEntity2.setCreatedDate(new Date());
        holdingsEntity2.setCreatedBy("etl");
        holdingsEntity2.setLastUpdatedDate(new Date());
        holdingsEntity2.setLastUpdatedBy("etl");
        holdingsEntity2.setOwningInstitutionId(1);
        holdingsEntity2.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        bibliographicEntity1.setHoldingsEntities(Arrays.asList(holdingsEntity1));
        bibliographicEntity1.setItemEntities(Arrays.asList(itemEntity));
        holdingsEntity1.setItemEntities(Arrays.asList(itemEntity));

        bibliographicEntity2.setHoldingsEntities(Arrays.asList(holdingsEntity2));
        bibliographicEntity2.setItemEntities(Arrays.asList(itemEntity));
        holdingsEntity2.setItemEntities(Arrays.asList(itemEntity));

        BibliographicEntity savedEntity1 = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity1);
        entityManager.refresh(savedEntity1);
        BibliographicEntity savedEntity2 = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity2);
        entityManager.refresh(savedEntity2);
    }

}