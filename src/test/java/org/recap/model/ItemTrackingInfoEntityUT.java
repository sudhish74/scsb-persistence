package org.recap.model;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.ItemTrackingInfoEntity;
import org.recap.repository.ItemTrackingInfoDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by hemalathas on 25/7/16.
 */
public class ItemTrackingInfoEntityUT extends BaseTestCase{

    @Autowired
    ItemTrackingInfoDetailsRepository itemTrackingInfoDetailsRepository;

    @Test
    public void testItemTrackingInfo(){
        Date today = new Date();
        ItemTrackingInfoEntity itemTrackingInfoEntity = new ItemTrackingInfoEntity();
        itemTrackingInfoEntity.setTrackingStatusId(1);
        itemTrackingInfoEntity.setBillNumber("101");
        itemTrackingInfoEntity.setUpdatedDateTime(today);
        ItemTrackingInfoEntity savedItemTrackingInfoEntity = itemTrackingInfoDetailsRepository.save(itemTrackingInfoEntity);
        assertNotNull(savedItemTrackingInfoEntity);
        assertNotNull(savedItemTrackingInfoEntity.getTrackingInfoId());
        assertTrue(savedItemTrackingInfoEntity.getTrackingStatusId() == 1);
        assertEquals(savedItemTrackingInfoEntity.getUpdatedDateTime(),today);
        assertEquals(savedItemTrackingInfoEntity.getBillNumber(),"101");
    }

}
