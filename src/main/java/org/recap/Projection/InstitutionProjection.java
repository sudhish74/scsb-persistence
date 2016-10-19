package org.recap.Projection;

import org.recap.model.InstitutionEntity;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by chenchulakshmig on 17/10/16.
 */
@Projection(name = "institutionProjection", types = InstitutionEntity.class)
public interface InstitutionProjection {

    Integer getInstitutionId();

    String getInstitutionCode();

    String getInstitutionName();
}
