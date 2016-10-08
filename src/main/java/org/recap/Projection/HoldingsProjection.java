package org.recap.Projection;

import org.recap.model.HoldingsEntity;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by chenchulakshmig on 8/10/16.
 */
@Projection(name = "holdingsProjection", types = HoldingsEntity.class)
public interface HoldingsProjection {

    Integer getHoldingsId();

    byte[] getContent();

    Date getCreatedDate();

    Date getLastUpdatedDate();

    String getOwningInstitutionHoldingsId();

    boolean isDeleted();

    String getCreatedBy();

    String getLastUpdatedBy();

    Integer getOwningInstitutionId();
}
