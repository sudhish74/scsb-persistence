package org.recap.repository;

import org.recap.Projection.ItemStatusProjection;
import org.recap.model.ItemStatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by chenchulakshmig on 6/13/16.
 */
@RepositoryRestResource(collectionResourceRel = "itemStatus", path = "itemStatus", excerptProjection = ItemStatusProjection.class)
public interface ItemStatusDetailsRepository extends CrudRepository<ItemStatusEntity, Integer> {
}
