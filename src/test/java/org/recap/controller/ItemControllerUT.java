package org.recap.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.recap.model.BibliographicEntity;
import org.recap.model.HoldingsEntity;
import org.recap.model.ItemEntity;
import org.recap.repository.HoldingsDetailsRepository;
import org.recap.repository.ItemDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StopWatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hemalathas on 28/7/16.
 */
public class ItemControllerUT extends BaseControllerUT{

    @Autowired
    ItemController itemController;

    @Autowired
    BibliographicController bibliographicController;


    public BibliographicEntity createBibliographic(){
        Random random = new Random();
        Date today = new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
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
        itemEntity.setOwningInstitutionItemId(".i1231");
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCustomerCode("PA");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setHoldingsEntity(holdingsEntity);

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));

        holdingsEntity.setItemEntities(Arrays.asList(itemEntity));
        BibliographicEntity savedBibliographicEntity = bibliographicController.create(bibliographicEntity);
        return savedBibliographicEntity;
    }

    @Test
    public void saveItem() throws Exception{
        BibliographicEntity bibliographicEntity = createBibliographic();
        assertNotNull(bibliographicEntity);
        assertNotNull(bibliographicEntity.getBibliographicId());
        assertNotNull(bibliographicEntity.getHoldingsEntities().get(0).getItemEntities().get(0).getItemId());
        Integer owningInstId = bibliographicEntity.getHoldingsEntities().get(0).getItemEntities().get(0).getOwningInstitutionId();
        String owningInstItemId = bibliographicEntity.getHoldingsEntities().get(0).getItemEntities().get(0).getOwningInstitutionItemId();
        assertNotNull(owningInstId);
        assertNotNull(owningInstItemId);
        ItemEntity savedItemEntity = itemController.findOne(owningInstId,owningInstItemId);
        assertNotNull(savedItemEntity);
        assertEquals(owningInstId, savedItemEntity.getOwningInstitutionId());
    }

    @Test
    public void counts() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("/item/count"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        long count = Long.valueOf(contentAsString);
        BibliographicEntity bibliographicEntity = createBibliographic();
        assertNotNull(bibliographicEntity);
        assertNotNull(bibliographicEntity.getBibliographicId());
        assertNotNull(bibliographicEntity.getHoldingsEntities().get(0).getItemEntities().get(0).getItemId());
        /*ItemEntity[] itemEntities = getItemEntities();
        assertEquals(count, itemEntities.length - 1);*/

    }


    @Test
    public void count() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("/item/count"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        long count = Long.valueOf(contentAsString);
        Date today = new Date();
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setCallNumberType("0");
        itemEntity.setCallNumber("callNum");
        itemEntity.setCreatedDate(today);
        itemEntity.setCreatedBy("etl");
        itemEntity.setLastUpdatedDate(today);
        itemEntity.setLastUpdatedBy("etl");
        itemEntity.setBarcode("1231");
        itemEntity.setOwningInstitutionItemId(".i1231");
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCustomerCode("PA");
        itemEntity.setItemAvailabilityStatusId(1);


        String itemJson = objectToJson(itemEntity);
        mvcResult = this.mockMvc.perform(post("/item/create")
                .contentType(contentType)
                .content(itemJson))
                .andExpect(status().isOk())
                .andReturn();
        ItemEntity savedItemEntity = (ItemEntity) jsonToObject(mvcResult.getResponse().getContentAsString(), ItemEntity.class);
        assertNotNull(savedItemEntity);
    }

    @Ignore
    @Test
    public void findAll() throws Exception{

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<ItemEntity> itemEntityList = Arrays.asList(getItemEntities());
        stopWatch.stop();
        System.out.println("Total time for " + itemEntityList.size() + " - bibs : " + stopWatch.getTotalTimeSeconds() + "ms");
    }

    private ItemEntity[] getItemEntities() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/item/findAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return (ItemEntity[]) jsonToObject(mvcResult.getResponse().getContentAsString(), ItemEntity[].class);

    }



    private int getMockItemId(){ return 1;}



}