package org.recap.repository;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.controller.BaseControllerUT;
import org.recap.model.BibliographicEntity;
import org.recap.model.HoldingsEntity;
import org.recap.model.ItemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by chenchulakshmig on 28/9/16.
 */
public class BibliographicDetailsRepositoryUT extends BaseControllerUT {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    ItemDetailsRepository itemDetailsRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional(noRollbackFor= {})
    @Rollback(false)
    public void saveAndFind() throws Exception {
        BibliographicEntity bibliographicEntity = saveSingleBibMultipleHoldingsItemsNNONO();

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


    @Test
    @Transactional(noRollbackFor= {})
    @Rollback(false)
    public void saveAndFind_001() throws Exception {
        BibliographicEntity bibliographicEntity = saveSingleBibMultipleHoldingsItemsONN();

        List<ItemEntity> nonDeletedItemEntities = bibliographicDetailsRepository.getNonDeletedItemEntities(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId());
        Long nonDeletedItemsCount = bibliographicDetailsRepository.getNonDeletedItemsCount(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId());
        assertTrue(nonDeletedItemEntities.size() == nonDeletedItemsCount);
//        assertEquals(nonDeletedItemEntities.get(1).getBarcode(), "12345-1");
    }
    /**
     * New Bib - New Holding, Old Holding - New Item, Old Item
     *
     * @return
     * @throws Exception
     */
    @Rollback(false)
    private BibliographicEntity saveSingleBibMultipleHoldingsItemsNNONO() throws Exception {
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
        holdingsEntity2.setCreatedBy("etl-Update");
        holdingsEntity2.setLastUpdatedDate(new Date());
        holdingsEntity2.setLastUpdatedBy("etl-Update");
        holdingsEntity2.setOwningInstitutionId(1);
//        holdingsEntity2.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));
        holdingsEntity2.setOwningInstitutionHoldingsId("836868686");
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
        itemEntity1.setCallNumber("Y.12321");
        itemEntity1.setCollectionGroupId(1);
        itemEntity1.setCallNumberType("1");

        ItemEntity itemEntity2 = new ItemEntity();
        itemEntity2.setCreatedDate(new Date());
        itemEntity2.setCreatedBy("etl");
        itemEntity2.setLastUpdatedDate(new Date());
        itemEntity2.setLastUpdatedBy("etl");
//        itemEntity2.setOwningInstitutionItemId(String.valueOf(random.nextInt()));
        itemEntity2.setOwningInstitutionItemId("744543721");
        itemEntity2.setOwningInstitutionId(1);
        itemEntity2.setCustomerCode("1");
        itemEntity2.setBarcode("1234");
        itemEntity2.setItemAvailabilityStatusId(1);
        itemEntity2.setCallNumber("ABC.12321");
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

    /*
        Old BIB - Old Holding - Old Item
     */
    @Rollback(false)
    private BibliographicEntity saveSingleBibMultipleHoldingsItemsOOO() throws Exception {
        String cretaed_By="etl-OOO-01";
        Random random = new Random();
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("mock Content".getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setCreatedBy(cretaed_By);
        bibliographicEntity.setLastUpdatedBy("etl");
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setOwningInstitutionBibId("101688");

        HoldingsEntity holdingsEntity1 = new HoldingsEntity();
        holdingsEntity1.setContent("mock holdings".getBytes());
        holdingsEntity1.setCreatedDate(new Date());
        holdingsEntity1.setCreatedBy(cretaed_By);
        holdingsEntity1.setLastUpdatedDate(new Date());
        holdingsEntity1.setLastUpdatedBy("etl");
        holdingsEntity1.setOwningInstitutionId(1);
        holdingsEntity1.setOwningInstitutionHoldingsId("109131");

        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setCreatedDate(new Date());
        itemEntity1.setCreatedBy(cretaed_By);
        itemEntity1.setLastUpdatedDate(new Date());
        itemEntity1.setLastUpdatedBy("etl");
        itemEntity1.setCustomerCode("1");
        itemEntity1.setItemAvailabilityStatusId(1);
        itemEntity1.setOwningInstitutionItemId("109131");
        itemEntity1.setOwningInstitutionId(1);
        itemEntity1.setBarcode("123456");
        itemEntity1.setCallNumber("Y.12321");
        itemEntity1.setCollectionGroupId(1);
        itemEntity1.setCallNumberType("1");

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity1));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity1));
        holdingsEntity1.setItemEntities(Arrays.asList(itemEntity1));


        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);
        return savedBibliographicEntity;
    }

    /*
        Old Bib, Old Holding , New Item
     */
    @Rollback(false)
    private BibliographicEntity saveSingleBibMultipleHoldingsItemsOON() throws Exception {
        Random random = new Random();
        String cretaed_By="etl-OON";
        String updated_By="etl";
        Date currentDate= new Date();
        String ran_owningInstitutionItemId = String.valueOf(random.nextInt());
        String barcode ="1234-3";

        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("mock Content".getBytes());
        bibliographicEntity.setCreatedDate(currentDate);
        bibliographicEntity.setCreatedBy(cretaed_By);
        bibliographicEntity.setLastUpdatedBy(updated_By);
        bibliographicEntity.setLastUpdatedDate(currentDate);
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setOwningInstitutionBibId("-1872860102");

        HoldingsEntity holdingsEntity1 = new HoldingsEntity();
        holdingsEntity1.setContent("mock holdings".getBytes());
        holdingsEntity1.setCreatedDate(currentDate);
        holdingsEntity1.setCreatedBy(cretaed_By);
        holdingsEntity1.setLastUpdatedDate(currentDate);
        holdingsEntity1.setLastUpdatedBy(updated_By);
        holdingsEntity1.setOwningInstitutionId(1);
        holdingsEntity1.setOwningInstitutionHoldingsId("-869363666");

        BibliographicEntity bib = bibliographicDetailsRepository.findByOwningInstitutionIdAndOwningInstitutionBibId(1,"-1872860102");
        List<ItemEntity>  items =  bib.getItemEntities();
        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setCreatedDate(currentDate);
        itemEntity1.setCreatedBy(cretaed_By);
        itemEntity1.setLastUpdatedDate(currentDate);
        itemEntity1.setLastUpdatedBy(updated_By);
        itemEntity1.setCustomerCode("1");
        itemEntity1.setItemAvailabilityStatusId(1);
        itemEntity1.setOwningInstitutionItemId(ran_owningInstitutionItemId);
        itemEntity1.setOwningInstitutionId(1);
        itemEntity1.setBarcode(barcode);
        itemEntity1.setCallNumber("Y.12321");
        itemEntity1.setCollectionGroupId(1);
        itemEntity1.setCallNumberType("1");

        items.add(itemEntity1);

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity1));
        bibliographicEntity.setItemEntities(items);
        holdingsEntity1.setItemEntities(items);


        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);

        entityManager.refresh(savedBibliographicEntity);
        return savedBibliographicEntity;
    }

    /*
        Old Bib, New Holding , New Item
     */
    @Rollback(false)
    private BibliographicEntity saveSingleBibMultipleHoldingsItemsONN() throws Exception {
        Random random = new Random();
        String cretaed_By="etl-OON";
        String updated_By="etl-OON-1";
        Date currentDate= new Date();
        String ran_owningInstitutionItemId = String.valueOf(random.nextInt());
        String ran_owningInstitutionHoldingsId = String.valueOf(random.nextInt());
        String barcode ="12345-3";

        BibliographicEntity bibliographicEntity = bibliographicDetailsRepository.findByOwningInstitutionIdAndOwningInstitutionBibId(1,"-1872860102");

        bibliographicEntity.setContent("mock Content".getBytes());
        bibliographicEntity.setCreatedDate(currentDate);
        bibliographicEntity.setCreatedBy(cretaed_By);
        bibliographicEntity.setLastUpdatedBy(updated_By);
        bibliographicEntity.setLastUpdatedDate(currentDate);
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setOwningInstitutionBibId("-1872860102");

        List<HoldingsEntity> holdings = bibliographicEntity.getHoldingsEntities();
        HoldingsEntity holdingsEntity1 = new HoldingsEntity();
        holdingsEntity1.setContent("mock holdings".getBytes());
        holdingsEntity1.setCreatedDate(currentDate);
        holdingsEntity1.setCreatedBy(cretaed_By);
        holdingsEntity1.setLastUpdatedDate(currentDate);
        holdingsEntity1.setLastUpdatedBy(updated_By);
        holdingsEntity1.setOwningInstitutionId(1);
        holdingsEntity1.setOwningInstitutionHoldingsId(ran_owningInstitutionHoldingsId);

        List<ItemEntity>  items =  bibliographicEntity.getItemEntities();

        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setCreatedDate(currentDate);
        itemEntity1.setCreatedBy(cretaed_By);
        itemEntity1.setLastUpdatedDate(currentDate);
        itemEntity1.setLastUpdatedBy(updated_By);
        itemEntity1.setCustomerCode("1");
        itemEntity1.setItemAvailabilityStatusId(1);
        itemEntity1.setOwningInstitutionItemId(ran_owningInstitutionItemId);
        itemEntity1.setOwningInstitutionId(1);
        itemEntity1.setBarcode(barcode);
        itemEntity1.setCallNumber("Y.12321");
        itemEntity1.setCollectionGroupId(1);
        itemEntity1.setCallNumberType("1");

        holdingsEntity1.setItemEntities(Arrays.asList(itemEntity1));
        items.add(itemEntity1);
        holdings.add(holdingsEntity1);

        bibliographicEntity.setHoldingsEntities(holdings);
        bibliographicEntity.setItemEntities(items);

        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);

        entityManager.refresh(savedBibliographicEntity);
        return savedBibliographicEntity;
    }

    private BibliographicEntity testData() throws Exception {
        Random random = new Random();
        String cretaed_By="etl-OON";
        String updated_By="etl-OON-1";
        Date currentDate= new Date();
        String ran_owningInstitutionItemId = String.valueOf(random.nextInt());
        String ran_owningInstitutionHoldingsId = String.valueOf(random.nextInt());
        String barcode ="12345-3";

//        BibliographicEntity bibliographicEntity = bibliographicDetailsRepository.findByOwningInstitutionIdAndOwningInstitutionBibId(1,"-1872860102");
        BibliographicEntity bibliographicEntity =new BibliographicEntity();

        bibliographicEntity.setContent("mock Content".getBytes());
        bibliographicEntity.setCreatedDate(currentDate);
        bibliographicEntity.setCreatedBy(cretaed_By);
        bibliographicEntity.setLastUpdatedBy(updated_By);
        bibliographicEntity.setLastUpdatedDate(currentDate);
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setOwningInstitutionBibId("-1872860102");

//        List<HoldingsEntity> holdings = bibliographicEntity.getHoldingsEntities();
        HoldingsEntity holdingsEntity1 = new HoldingsEntity();
        holdingsEntity1.setContent("mock holdings".getBytes());
        holdingsEntity1.setCreatedDate(currentDate);
        holdingsEntity1.setCreatedBy(cretaed_By);
        holdingsEntity1.setLastUpdatedDate(currentDate);
        holdingsEntity1.setLastUpdatedBy(updated_By);
        holdingsEntity1.setOwningInstitutionId(1);
        holdingsEntity1.setOwningInstitutionHoldingsId("-969682412");

        HoldingsEntity holdingsEntity2 = new HoldingsEntity();
        holdingsEntity2.setContent("mock holdings".getBytes());
        holdingsEntity2.setCreatedDate(currentDate);
        holdingsEntity2.setCreatedBy(cretaed_By);
        holdingsEntity2.setLastUpdatedDate(currentDate);
        holdingsEntity2.setLastUpdatedBy(updated_By);
        holdingsEntity2.setOwningInstitutionId(1);
        holdingsEntity2.setOwningInstitutionHoldingsId(ran_owningInstitutionHoldingsId);

//        List<ItemEntity>  items =  bibliographicEntity.getItemEntities();

        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setCreatedDate(currentDate);
        itemEntity1.setCreatedBy(cretaed_By);
        itemEntity1.setLastUpdatedDate(currentDate);
        itemEntity1.setLastUpdatedBy(updated_By);
        itemEntity1.setCustomerCode("1");
        itemEntity1.setItemAvailabilityStatusId(1);
        itemEntity1.setOwningInstitutionItemId("-1170463769");
        itemEntity1.setOwningInstitutionId(1);
        itemEntity1.setBarcode(barcode);
        itemEntity1.setCallNumber("Y.12321");
        itemEntity1.setCollectionGroupId(1);
        itemEntity1.setCallNumberType("1");

        ItemEntity itemEntity2 = new ItemEntity();
        itemEntity2.setCreatedDate(currentDate);
        itemEntity2.setCreatedBy(cretaed_By);
        itemEntity2.setLastUpdatedDate(currentDate);
        itemEntity2.setLastUpdatedBy(updated_By);
        itemEntity2.setCustomerCode("1");
        itemEntity2.setItemAvailabilityStatusId(1);
        itemEntity2.setOwningInstitutionItemId(ran_owningInstitutionItemId);
        itemEntity2.setOwningInstitutionId(1);
        itemEntity2.setBarcode(barcode);
        itemEntity2.setCallNumber("Y.12321");
        itemEntity2.setCollectionGroupId(1);
        itemEntity2.setCallNumberType("1");

//        items.get(0).setBarcode("1234567-A");

        holdingsEntity1.setItemEntities(Arrays.asList(itemEntity1));
        holdingsEntity2.setItemEntities(Arrays.asList(itemEntity2));
//        items.add(itemEntity1);
//        holdings.add(holdingsEntity1);

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity1,holdingsEntity2));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity1,itemEntity2));

        return bibliographicEntity;
    }

    @Test
    public void testLoop()throws Exception{
        BibliographicEntity bibliographicEntity = testData();
        BibliographicEntity fetchBibliographicEntity = bibliographicDetailsRepository.findByOwningInstitutionIdAndOwningInstitutionBibId(1,"-1324424004");

        List<HoldingsEntity> fetchHoldingsEntities =fetchBibliographicEntity.getHoldingsEntities();
        List<HoldingsEntity> holdingsEntities = new ArrayList<HoldingsEntity>(bibliographicEntity.getHoldingsEntities());

        logger.info("fetchHoldingsEntities = "+fetchHoldingsEntities.size());
        logger.info("holdingsEntities = "+holdingsEntities.size());

        for (Iterator iholdings=holdingsEntities.iterator();iholdings.hasNext();) {
            HoldingsEntity holdingsEntity =(HoldingsEntity) iholdings.next();
            for (int j=0;j<fetchHoldingsEntities.size();j++) {
                HoldingsEntity fetchHolding=fetchHoldingsEntities.get(j);
                if(fetchHolding.getOwningInstitutionHoldingsId().equalsIgnoreCase(holdingsEntity.getOwningInstitutionHoldingsId())  && fetchHolding.getOwningInstitutionId().intValue() == holdingsEntity.getOwningInstitutionId().intValue()) {
                    fetchHolding = copytoHoldingsEntity(fetchHolding,holdingsEntity);
                    iholdings.remove();
                }
            }
        }
        fetchHoldingsEntities.addAll(holdingsEntities);
        logger.info("Holding Final Count = "+fetchHoldingsEntities.size());
        assertEquals(fetchHoldingsEntities.size(),3);
        // Item
        List<ItemEntity> fetchItemsEntities =fetchBibliographicEntity.getItemEntities();
        List<ItemEntity> itemsEntities = new ArrayList<ItemEntity>(bibliographicEntity.getItemEntities());

        logger.info("fetchItemssEntities = "+fetchItemsEntities.size());
        logger.info("ItemsEntities = "+itemsEntities.size());

        for (Iterator iItems=itemsEntities.iterator();iItems.hasNext();) {
            ItemEntity itemEntity =(ItemEntity) iItems.next();
            for (Iterator ifetchItems=fetchItemsEntities.iterator();ifetchItems.hasNext();) {
                ItemEntity fetchItem=(ItemEntity) ifetchItems.next();
                if(fetchItem.getOwningInstitutionItemId().equalsIgnoreCase(itemEntity.getOwningInstitutionItemId())  && fetchItem.getOwningInstitutionId().intValue() == itemEntity.getOwningInstitutionId().intValue()) {
                    fetchItem = copytoHoldingsEntity(fetchItem,itemEntity);
                    iItems.remove();
                }
            }
        }
        fetchItemsEntities.addAll(itemsEntities);
        logger.info("Item Final Count = "+fetchItemsEntities.size());
        assertEquals(fetchItemsEntities.size(),3);
    }

    @Test
    public void updateBIBHoldingItem()throws Exception{

    }

    private HoldingsEntity copytoHoldingsEntity(HoldingsEntity fetchHoldingsEntity,HoldingsEntity holdingsEntity){
        fetchHoldingsEntity.setContent(holdingsEntity.getContent()); ;
        fetchHoldingsEntity.setCreatedBy(holdingsEntity.getCreatedBy());
        fetchHoldingsEntity.setCreatedDate(holdingsEntity.getCreatedDate());
        fetchHoldingsEntity.setDeleted(holdingsEntity.isDeleted());
        fetchHoldingsEntity.setLastUpdatedBy(holdingsEntity.getLastUpdatedBy());
        fetchHoldingsEntity.setLastUpdatedDate(holdingsEntity.getLastUpdatedDate());
        return fetchHoldingsEntity;
    }

    private ItemEntity copytoHoldingsEntity(ItemEntity fetchItemEntity,ItemEntity itemEntity){
        fetchItemEntity.setBarcode(itemEntity.getBarcode()); ;
        fetchItemEntity.setCreatedBy(itemEntity.getCreatedBy());
        fetchItemEntity.setCreatedDate(itemEntity.getCreatedDate());
        fetchItemEntity.setDeleted(itemEntity.isDeleted());
        fetchItemEntity.setLastUpdatedBy(itemEntity.getLastUpdatedBy());
        fetchItemEntity.setLastUpdatedDate(itemEntity.getLastUpdatedDate());

        fetchItemEntity.setCallNumber(itemEntity.getCallNumber());
        fetchItemEntity.setCustomerCode(itemEntity.getCustomerCode());
        fetchItemEntity.setCallNumberType(itemEntity.getCallNumberType());
        fetchItemEntity.setItemAvailabilityStatusId(itemEntity.getItemAvailabilityStatusId());
        fetchItemEntity.setCopyNumber(itemEntity.getCopyNumber());
        fetchItemEntity.setCollectionGroupId(itemEntity.getCollectionGroupId());
        fetchItemEntity.setUseRestrictions(itemEntity.getUseRestrictions());
        fetchItemEntity.setVolumePartYear(itemEntity.getVolumePartYear());
        return fetchItemEntity;
    }
}