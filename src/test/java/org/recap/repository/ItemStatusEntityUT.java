package org.recap.repository;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.ItemStatusEntity;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 25/7/16.
 */
public class ItemStatusEntityUT extends BaseTestCase{

    @Autowired
    ItemStatusDetailsRepository  itemStatusDetailsRepository;

    @Test
    public void itemStatus(){
        ItemStatusEntity itemStatusEntity = new ItemStatusEntity();
        itemStatusEntity.setStatusCode("RecentlyReturned");
        itemStatusEntity.setStatusDescription("RecentlyReturned");
        ItemStatusEntity itemStatusEntity1 = itemStatusDetailsRepository.save(itemStatusEntity);
        assertNotNull(itemStatusEntity1);
        System.out.println(itemStatusEntity1.getItemStatusId());
        assertEquals(itemStatusEntity1.getStatusCode(),"RecentlyReturned");
        assertEquals(itemStatusEntity1.getStatusDescription(),"RecentlyReturned");
        itemStatusDetailsRepository.delete(itemStatusEntity);
    }
}
