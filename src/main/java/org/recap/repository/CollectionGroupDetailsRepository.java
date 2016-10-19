package org.recap.repository;

import org.recap.Projection.CollectionGroupProjection;
import org.recap.model.CollectionGroupEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by chenchulakshmig on 6/13/16.
 */
@RepositoryRestResource(collectionResourceRel = "collectionGroup", path = "collectionGroup", excerptProjection = CollectionGroupProjection.class)
public interface CollectionGroupDetailsRepository extends CrudRepository<CollectionGroupEntity, Integer> {
    CollectionGroupEntity findByCollectionGroupCode(@Param("collectionGroupCode") String collectionGroupCode);
}
