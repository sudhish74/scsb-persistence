package org.recap.model;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.repository.AuditDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by hemalathas on 27/7/16.
 */
public class AuditEntityUT extends BaseTestCase{

    @Autowired
    AuditDetailsRepository auditDetailsRepository;

    @Test
    public void testAuditEntity(){
        AuditEntity auditEntity = new AuditEntity();
        Date today = new Date();
        auditEntity.setDateTimeUpdated(new Date());
        auditEntity.setColumnUpdated("test");
        auditEntity.setTableName("test");
        auditEntity.setValue("test");
        AuditEntity savedAuditEntity = auditDetailsRepository.save(auditEntity);
        assertNotNull(savedAuditEntity);
        assertNotNull(savedAuditEntity.getAuditId());
        assertEquals(savedAuditEntity.getDateTimeUpdated(),today);
        assertEquals(savedAuditEntity.getColumnUpdated(),"test");
        assertEquals(savedAuditEntity.getTableName(),"test");
        assertEquals(savedAuditEntity.getValue(),"test");
    }

}