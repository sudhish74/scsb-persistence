package org.recap.model;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.recap.controller.BaseControllerUT;
import org.recap.repository.InstitutionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hemalathas on 25/7/16.
 */
public class InstitutionEntityUT extends BaseControllerUT {
    @Autowired
    InstitutionDetailsRepository institutionDetailRepository;

    @Test
    public void institutionEntity() throws Exception {
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("UC");
        institutionEntity.setInstitutionName("University of Chicago");
        InstitutionEntity entity = institutionDetailRepository.save(institutionEntity);
        assertNotNull(entity);
        assertNotNull(entity.getInstitutionId());
        assertEquals(entity.getInstitutionCode(), "UC");
        assertEquals(entity.getInstitutionName(), "University of Chicago");
        institutionDetailRepository.delete(institutionEntity);
    }

    @Test
    public void findAllRestService() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/institution"))
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);
        JSONObject jsonResponse = new JSONObject(response).getJSONObject("_embedded");
        JSONArray institutionEntities = jsonResponse.getJSONArray("institution");
        assertNotNull(institutionEntities);
    }
}
