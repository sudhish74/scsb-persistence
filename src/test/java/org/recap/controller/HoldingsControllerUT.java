package org.recap.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.recap.model.BibliographicEntity;
import org.recap.model.HoldingsEntity;
import org.recap.model.ItemEntity;
import org.recap.repository.HoldingsDetailsRepository;
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
public class HoldingsControllerUT extends BaseControllerUT{

    @Autowired
    HoldingsController holdingsController;

    @Autowired
    BibliographicController bibliographicController;

    @Autowired
    HoldingsDetailsRepository holdingsDetailsRepository;



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
        bibliographicEntity.setItemEntities(null);

        holdingsEntity.setItemEntities(null);
        BibliographicEntity savedBibliographicEntity = bibliographicController.create(bibliographicEntity);
        return savedBibliographicEntity;
    }


    @Test
    public void saveAndFindHoldingsEntity() throws Exception{
        BibliographicEntity savedBibliographicEntity = createBibliographic();
        assertNotNull(savedBibliographicEntity);
        assertNotNull(savedBibliographicEntity.getBibliographicId());
        assertNotNull(savedBibliographicEntity.getHoldingsEntities().get(0).getHoldingsId());
        String owningInstHoldingId = savedBibliographicEntity.getHoldingsEntities().get(0).getOwningInstitutionHoldingsId();
        HoldingsEntity savedHoldingsEntity = holdingsController.findOne(owningInstHoldingId);
        assertNotNull(savedHoldingsEntity);
        assertEquals(owningInstHoldingId, savedHoldingsEntity.getOwningInstitutionHoldingsId());
        HoldingsEntity byHoldingsId = holdingsDetailsRepository.findByHoldingsId(savedHoldingsEntity.getHoldingsId());
        assertNotNull(byHoldingsId);
    }

    @Test
    public void counts() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("/holdings/count"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        long count = Long.valueOf(contentAsString);
        BibliographicEntity savedBibliographicEntity = createBibliographic();
        assertNotNull(savedBibliographicEntity);
        assertNotNull(savedBibliographicEntity.getBibliographicId());
        assertNotNull(savedBibliographicEntity.getHoldingsEntities().get(0).getHoldingsId());
                /*HoldingsEntity[] holdingsEntities = getHoldingEntities();
        assertEquals(count, holdingsEntities.length - 1);*/

    }

    @Test
    public void count() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("/holdings/count"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        long count = Long.valueOf(contentAsString);
        Random random = new Random();
        Date today = new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(today);
        holdingsEntity.setCreatedBy("etl");
        holdingsEntity.setLastUpdatedDate(today);
        holdingsEntity.setLastUpdatedBy("etl");
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        String holdingJson = objectToJson(holdingsEntity);
        mvcResult = this.mockMvc.perform(post("/holdings/create")
                .contentType(contentType)
                .content(holdingJson))
                .andExpect(status().isOk())
                .andReturn();
        HoldingsEntity savedHoldingsEntity = (HoldingsEntity) jsonToObject(mvcResult.getResponse().getContentAsString(), HoldingsEntity.class);
        assertNotNull(savedHoldingsEntity);
        assertNotNull(savedHoldingsEntity.getOwningInstitutionHoldingsId());


    }

    @Ignore
    @Test
    public void findAll() throws Exception{

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<HoldingsEntity> holdingEntityList = Arrays.asList(getHoldingEntities());
        stopWatch.stop();
        System.out.println("Total time for " + holdingEntityList.size() + " - bibs : " + stopWatch.getTotalTimeSeconds() + "ms");
    }

    private HoldingsEntity[] getHoldingEntities() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/holdings/findAll")
                .param("pageNum",String.valueOf(1))
                .param("numberOfRecords",String.valueOf(2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return (HoldingsEntity[]) jsonToObject(mvcResult.getResponse().getContentAsString(), HoldingsEntity[].class);

    }




}