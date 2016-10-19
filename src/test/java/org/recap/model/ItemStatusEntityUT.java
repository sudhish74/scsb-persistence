package org.recap.model;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.recap.controller.BaseControllerUT;
import org.recap.repository.ItemStatusDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hemalathas on 25/7/16.
 */
public class ItemStatusEntityUT extends BaseControllerUT {

    @Autowired
    ItemStatusDetailsRepository itemStatusDetailsRepository;

    @Test
    public void itemStatus() {
        ItemStatusEntity itemStatusEntity = new ItemStatusEntity();
        itemStatusEntity.setStatusCode("RecentlyReturned");
        itemStatusEntity.setStatusDescription("RecentlyReturned");
        ItemStatusEntity itemStatusEntity1 = itemStatusDetailsRepository.save(itemStatusEntity);
        assertNotNull(itemStatusEntity1);
        System.out.println(itemStatusEntity1.getItemStatusId());
        assertEquals(itemStatusEntity1.getStatusCode(), "RecentlyReturned");
        assertEquals(itemStatusEntity1.getStatusDescription(), "RecentlyReturned");
        itemStatusDetailsRepository.delete(itemStatusEntity);
    }

    @Test
    public void findAllRestService() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/itemStatus"))
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);
        JSONObject jsonResponse = new JSONObject(response).getJSONObject("_embedded");
        JSONArray itemStatusEntities = jsonResponse.getJSONArray("itemStatus");
        assertNotNull(itemStatusEntities);
    }
}
