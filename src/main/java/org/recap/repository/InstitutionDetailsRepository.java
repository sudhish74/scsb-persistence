package org.recap.repository;

import org.recap.Projection.InstitutionProjection;
import org.recap.model.InstitutionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by chenchulakshmig on 6/13/16.
 */
@RepositoryRestResource(collectionResourceRel = "institution", path = "institution", excerptProjection = InstitutionProjection.class)
public interface InstitutionDetailsRepository extends CrudRepository<InstitutionEntity, Integer> {
}
