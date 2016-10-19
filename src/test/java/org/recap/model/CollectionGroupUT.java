package org.recap.model;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.recap.controller.BaseControllerUT;
import org.recap.repository.CollectionGroupDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hemalathas on 25/7/16.
 */
public class CollectionGroupUT extends BaseControllerUT {
    @Autowired
    CollectionGroupDetailsRepository collectionGroupDetailsRepository;

    @Test
    public void saveAndFind() throws Exception {
        assertNotNull(collectionGroupDetailsRepository);
        String collectionGroupCode = "test";

        CollectionGroupEntity collectionGroupEntity = new CollectionGroupEntity();
        collectionGroupEntity.setCollectionGroupCode(collectionGroupCode);
        collectionGroupEntity.setCollectionGroupDescription("test");
        Date date = new Date();
        collectionGroupEntity.setCreatedDate(date);
        collectionGroupEntity.setLastUpdatedDate(date);

        CollectionGroupEntity savedCollectionGroupEntity = collectionGroupDetailsRepository.save(collectionGroupEntity);
        assertNotNull(savedCollectionGroupEntity);
        assertNotNull(savedCollectionGroupEntity.getCollectionGroupId());
        assertEquals(savedCollectionGroupEntity.getCollectionGroupCode(), collectionGroupCode);
        assertEquals(savedCollectionGroupEntity.getCollectionGroupDescription(), "test");
        assertEquals(savedCollectionGroupEntity.getCreatedDate(), date);
        assertEquals(savedCollectionGroupEntity.getLastUpdatedDate(), date);

        CollectionGroupEntity byCollectionGroupCode = collectionGroupDetailsRepository.findByCollectionGroupCode(collectionGroupCode);
        assertNotNull(byCollectionGroupCode);

        MvcResult mvcResult = this.mockMvc.perform(get("/collectionGroup/search/findByCollectionGroupCode")
                .param("collectionGroupCode", collectionGroupCode))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);
    }

    @Test
    public void findAllRestService() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/collectionGroup"))
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);
        JSONObject jsonResponse = new JSONObject(response).getJSONObject("_embedded");
        JSONArray collectionGroupEntities = jsonResponse.getJSONArray("collectionGroup");
        assertNotNull(collectionGroupEntities);
    }
}
