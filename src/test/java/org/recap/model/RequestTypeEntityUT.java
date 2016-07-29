package org.recap.model;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.repository.RequestTypeDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by hemalathas on 27/7/16.
 */
public class RequestTypeEntityUT extends BaseTestCase{

    @Autowired
    RequestTypeDetailsRepository requestTypeDetailsRepository;

    @Test
    public void testRequestType(){
        RequestTypeEntity requestTypeEntity = new RequestTypeEntity();
        requestTypeEntity.setRequestTypeCode("tst");
        requestTypeEntity.setRequestTypeDescription("test");
        RequestTypeEntity savedReRequestTypeEntity = requestTypeDetailsRepository.save(requestTypeEntity);
        assertNotNull(savedReRequestTypeEntity);
        assertNotNull(savedReRequestTypeEntity.getRequestTypeId());
        assertEquals(savedReRequestTypeEntity.getRequestTypeCode(),"tst");
        assertEquals(savedReRequestTypeEntity.getRequestTypeDescription(),"test");
    }

}