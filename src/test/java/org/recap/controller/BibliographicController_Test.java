package org.recap.controller;

import org.junit.Test;
import org.recap.model.BibliographicEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StopWatch;

import java.util.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by chenchulakshmig on 6/13/16.
 */
public class BibliographicController_Test extends BaseControllerTestCase {

    @Test
    public void saveBibliographic() throws Exception {
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("Mock Bib Content");
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setOwningInstitutionBibId("555");
        bibliographicEntity.setOwningInstitutionId(1);

        String bibliographicJson = objectToJson(bibliographicEntity);
        MvcResult mvcResult = this.mockMvc.perform(post("/bibliographic/create")
                .contentType(contentType)
                .content(bibliographicJson))
                .andExpect(status().isOk())
                .andReturn();
        BibliographicEntity savedBibliographicEntity = (BibliographicEntity) jsonToObject(mvcResult.getResponse().getContentAsString(), BibliographicEntity.class);
        Integer bibliographicId = savedBibliographicEntity.getBibliographicId();
        assertNotNull(bibliographicId);

        MvcResult savedResult = this.mockMvc.perform(get("/bibliographic/findOne")
                .param("bibliographicId", String.valueOf(bibliographicId)))
                .andExpect(status().isOk())
                .andReturn();
        savedBibliographicEntity = (BibliographicEntity) jsonToObject(savedResult.getResponse().getContentAsString(), BibliographicEntity.class);
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
        bibliographicEntity.setContent("Mock Bib Content");
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setOwningInstitutionBibId("111");
        bibliographicEntity.setOwningInstitutionId(1);

        String bibliographicJson = objectToJson(bibliographicEntity);
        mvcResult = this.mockMvc.perform(post("/bibliographic/create")
                .contentType(contentType)
                .content(bibliographicJson))
                .andExpect(status().isOk())
                .andReturn();
        BibliographicEntity savedBibliographicEntity = (BibliographicEntity) jsonToObject(mvcResult.getResponse().getContentAsString(), BibliographicEntity.class);
        Integer bibliographicId = savedBibliographicEntity.getBibliographicId();
        assertNotNull(bibliographicId);

        BibliographicEntity[] bibliographicEntities = getBibliographicEntities();
        assertEquals(count, bibliographicEntities.length - 1);
    }

    @Test
    public void find() throws Exception {
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("Mock Bib Content");
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setOwningInstitutionBibId("222");
        bibliographicEntity.setOwningInstitutionId(1);

        String bibliographicJson = objectToJson(bibliographicEntity);
        MvcResult mvcResult = this.mockMvc.perform(post("/bibliographic/create")
                .contentType(contentType)
                .content(bibliographicJson))
                .andExpect(status().isOk())
                .andReturn();
        BibliographicEntity savedBibliographicEntity = (BibliographicEntity) jsonToObject(mvcResult.getResponse().getContentAsString(), BibliographicEntity.class);
        Integer bibliographicId1 = savedBibliographicEntity.getBibliographicId();
        assertNotNull(bibliographicId1);

        bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("Mock Bib Content");
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setOwningInstitutionBibId("333");
        bibliographicEntity.setOwningInstitutionId(1);

        bibliographicJson = objectToJson(bibliographicEntity);
        mvcResult = this.mockMvc.perform(post("/bibliographic/create")
                .contentType(contentType)
                .content(bibliographicJson))
                .andExpect(status().isOk())
                .andReturn();
        savedBibliographicEntity = (BibliographicEntity) jsonToObject(mvcResult.getResponse().getContentAsString(), BibliographicEntity.class);
        Integer bibliographicId2 = savedBibliographicEntity.getBibliographicId();
        assertNotNull(bibliographicId2);

        bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("Mock Bib Content");
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setOwningInstitutionBibId("444");
        bibliographicEntity.setOwningInstitutionId(1);

        bibliographicJson = objectToJson(bibliographicEntity);
        mvcResult = this.mockMvc.perform(post("/bibliographic/create")
                .contentType(contentType)
                .content(bibliographicJson))
                .andExpect(status().isOk())
                .andReturn();
        savedBibliographicEntity = (BibliographicEntity) jsonToObject(mvcResult.getResponse().getContentAsString(), BibliographicEntity.class);
        Integer bibliographicId3 = savedBibliographicEntity.getBibliographicId();
        assertNotNull(bibliographicId3);

        BibliographicEntity[] bibliographicEntities = getBibliographicEntities(bibliographicId1, bibliographicId3);
        assertTrue(bibliographicEntities.length == 3);
    }

    @Test
    public void findAll() throws Exception{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<BibliographicEntity> bibliographicEntityList = Arrays.asList(getBibliographicEntities());
        stopWatch.stop();
        System.out.println("Total time for " + bibliographicEntityList.size() + " - bibs : " + stopWatch.getTotalTimeSeconds() + "ms");
    }

    private BibliographicEntity[] getBibliographicEntities() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/bibliographic/findAll"))
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