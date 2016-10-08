package org.recap.Projection;

import org.recap.model.BibliographicEntity;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by chenchulakshmig on 8/10/16.
 */
@Projection(name = "bibliographicProjection", types = BibliographicEntity.class)
public interface BibliographicProjection {

    Integer getBibliographicId();

    byte[] getContent();

    Integer getOwningInstitutionId();

    Date getCreatedDate();

    String getCreatedBy();

    Date getLastUpdatedDate();

    String getLastUpdatedBy();

    String getOwningInstitutionBibId();

    boolean isDeleted();
}
