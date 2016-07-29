package org.recap.model;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.InstitutionEntity;
import org.recap.repository.InstitutionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 25/7/16.
 */
public class InstitutionEntityUT extends BaseTestCase{
    @Autowired
    InstitutionDetailsRepository institutionDetailRepository;

    @Test
    public void institutionEntity(){
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("UC");
        institutionEntity.setInstitutionName("University of Chicago");
        InstitutionEntity entity = institutionDetailRepository.save(institutionEntity);
        assertNotNull(entity);
        assertNotNull(entity.getInstitutionId());
        assertEquals(entity.getInstitutionCode(),"UC");
        assertEquals(entity.getInstitutionName(),"University of Chicago");
        institutionDetailRepository.delete(institutionEntity);
    }
}
