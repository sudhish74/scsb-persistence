package org.recap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.recap.model.BibliographicEntity;
import org.springframework.http.MediaType;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by chenchulakshmig on 6/13/16.
 */
public class BibliographicControllerUT extends BaseControllerUT {


    @Test
    public void saveAndFindBibliographic() throws Exception {
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        Random random = new Random();
        Date today = new Date();
        bibliographicEntity.setContent("mock Content".getBytes());
        bibliographicEntity.setCreatedDate(today);
        bibliographicEntity.setCreatedBy("etl");
        bibliographicEntity.setLastUpdatedBy("etl");
        bibliographicEntity.setLastUpdatedDate(today);
        int owningInstitutionId = 1;
        bibliographicEntity.setOwningInstitutionId(owningInstitutionId);
        String owningInstitutionBibId = String.valueOf(random.nextInt());
        bibliographicEntity.setOwningInstitutionBibId(owningInstitutionBibId);
        bibliographicEntity.setHoldingsEntities(null);
        bibliographicEntity.setItemEntities(null);
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = this.mockMvc.perform(post("/bibliographic/create")
                .contentType(contentType)
                .content(objectMapper.writeValueAsString(bibliographicEntity)))
                .andExpect(status().isOk())
                .andReturn();
        Integer bibliographicId = (Integer) jsonToObject(mvcResult.getResponse().getContentAsString(), Integer.class);
        assertNotNull(bibliographicId);

        MvcResult savedResult = this.mockMvc.perform(get("/bibliographic/findOne")
                .param("owningInstitutionId", String.valueOf(owningInstitutionId))
                .param("owningInstitutionBibId", String.valueOf(owningInstitutionBibId)))
                .andExpect(status().isOk())
                .andReturn();
        BibliographicEntity savedBibliographicEntity = (BibliographicEntity) jsonToObject(savedResult.getResponse().getContentAsString(), BibliographicEntity.class);
        assertNotNull(savedBibliographicEntity);
        assertEquals(bibliographicId, savedBibliographicEntity.getBibliographicId());
    }

    @Test
    public void count() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/bibliographic/count"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        long count = Long.valueOf(contentAsString);

        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        Random random = new Random();
        Date today = new Date();
        bibliographicEntity.setContent("mock Content".getBytes());
        bibliographicEntity.setCreatedDate(today);
        bibliographicEntity.setCreatedBy("etl");
        bibliographicEntity.setLastUpdatedBy("etl");
        bibliographicEntity.setLastUpdatedDate(today);
        bibliographicEntity.setOwningInstitutionId(1);
        String owningInstitutionBibId = String.valueOf(random.nextInt());
        bibliographicEntity.setOwningInstitutionBibId(owningInstitutionBibId);
        bibliographicEntity.setHoldingsEntities(null);
        bibliographicEntity.setItemEntities(null);
        ObjectMapper objectMapper = new ObjectMapper();
        mvcResult = this.mockMvc.perform(post("/bibliographic/create")
                .contentType(contentType)
                .content(objectMapper.writeValueAsString(bibliographicEntity)))
                .andExpect(status().isOk())
                .andReturn();
        Integer bibliographicId = (Integer) jsonToObject(mvcResult.getResponse().getContentAsString(), Integer.class);
        assertNotNull(bibliographicId);

        /*BibliographicEntity[] bibliographicEntities = getBibliographicEntities();
        assertEquals(count, bibliographicEntities.length - 1);*/
    }


    @Ignore
    @Test
    public void findAll() throws Exception{

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<BibliographicEntity> bibliographicEntityList = Arrays.asList(getBibliographicEntities());
        stopWatch.stop();
        System.out.println("Total time for " + bibliographicEntityList.size() + " - bibs : " + stopWatch.getTotalTimeSeconds() + "ms");
    }

    private BibliographicEntity[] getBibliographicEntities() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/bibliographic/findAll")
        .param("pageNum",String.valueOf(1))
        .param("numberOfRecords",String.valueOf(2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return (BibliographicEntity[]) jsonToObject(mvcResult.getResponse().getContentAsString(), BibliographicEntity[].class);

    }

    private BibliographicEntity[] getBibliographicEntities(Integer fromId, Integer toId) throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/bibliographic/findByRangeOfIds")
                .param("fromId", String.valueOf(fromId))
                .param("toId", String.valueOf(toId)))
                .andExpect(status().isOk())
                .andReturn();
        BibliographicEntity[] bibliographicEntities = (BibliographicEntity[]) jsonToObject(mvcResult.getResponse().getContentAsString(), BibliographicEntity[].class);
        return bibliographicEntities;
    }



}