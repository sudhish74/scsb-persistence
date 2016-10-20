package org.recap.repository;

import org.recap.model.CustomerCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by rajeshbabuk on 18/10/16.
 */
@RepositoryRestResource(collectionResourceRel = "customerCode", path = "customerCode")
public interface CustomerCodeDetailsRepository extends JpaRepository<CustomerCodeEntity, Integer> {

    CustomerCodeEntity findByCustomerCode(@Param("customerCode") String customerCode);
}
