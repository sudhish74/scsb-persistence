package org.recap.model;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.repository.InstitutionDetailsRepository;
import org.recap.repository.PatronDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by hemalathas on 25/7/16.
 */
public class PatronEntityUT extends BaseTestCase{

    @Autowired
    PatronDetailsRepository patronDetailsRepository;

    @Autowired
    InstitutionDetailsRepository institutionDetailRepository;

    @Test
    public void savePatronDetails(){
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("UC");
        institutionEntity.setInstitutionName("University of Chicago");
        InstitutionEntity entity = institutionDetailRepository.save(institutionEntity);
        assertNotNull(entity);
        assertNotNull(entity.getInstitutionId());

        PatronEntity patronEntity = new PatronEntity();
        patronEntity.setInstitutionIdentifier("PUL");
        patronEntity.setInstitutionId(entity.getInstitutionId());
        patronEntity.setEmailId("tst.tst@dev.org");
        PatronEntity savedPatronEntity = patronDetailsRepository.save(patronEntity);
        assertNotNull(savedPatronEntity);
        assertNotNull(savedPatronEntity.getPatronId());
        assertEquals(savedPatronEntity.getInstitutionIdentifier(),"PUL");
        assertEquals(savedPatronEntity.getEmailId(),"tst.tst@dev.org");
        assertEquals(savedPatronEntity.getInstitutionId(),entity.getInstitutionId());
    }
}