package org.recap.repository;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.CustomerCodeEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by rajeshbabuk on 18/10/16.
 */
public class CustomerCodeDetailsRepositoryUT extends BaseTestCase {

    @Autowired
    CustomerCodeDetailsRepository customerCodeDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void findByCustomerCode() throws Exception {

        CustomerCodeEntity customerCodeEntity = new CustomerCodeEntity();
        customerCodeEntity.setCustomerCode("ZZ");
        customerCodeEntity.setDescription("Desc ZZ");
        customerCodeEntity.setOwningInstitutionId(3);
        customerCodeEntity.setDeliveryRestrictions("ZZ,CC");

        CustomerCodeEntity saveCustomerCodeEntity = customerCodeDetailsRepository.saveAndFlush(customerCodeEntity);
        entityManager.refresh(saveCustomerCodeEntity);
        assertNotNull(saveCustomerCodeEntity);
        assertNotNull(saveCustomerCodeEntity.getCustomerCodeId());
        assertNotNull(saveCustomerCodeEntity.getCustomerCode());

        CustomerCodeEntity byCustomerCode = customerCodeDetailsRepository.findByCustomerCode(saveCustomerCodeEntity.getCustomerCode());
        assertNotNull(byCustomerCode);
        assertNotNull(byCustomerCode.getCustomerCode());
        assertTrue(byCustomerCode.getCustomerCode().equalsIgnoreCase("ZZ"));
        assertNotNull(byCustomerCode.getInstitutionEntity());
    }
}
