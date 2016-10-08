package org.recap.Projection;

import org.recap.model.CollectionGroupEntity;
import org.recap.model.InstitutionEntity;
import org.recap.model.ItemEntity;
import org.recap.model.ItemStatusEntity;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by chenchulakshmig on 7/10/16.
 */
@Projection(name = "edit", types = ItemEntity.class)
public interface ItemProjection {

    Integer getItemId();

    String getBarcode();

    String getCustomerCode();

    String getCallNumber();

    String getCallNumberType();

    Integer getItemAvailabilityStatusId();

    Integer getOwningInstitutionId();

    Integer getCopyNumber();

    Integer getCollectionGroupId();

    Date getCreatedDate();

    Date getLastUpdatedDate();

    String getUseRestrictions();

    String getVolumePartYear();

    String getOwningInstitutionItemId();

    String getCreatedBy();

    String getLastUpdatedBy();

    boolean isDeleted();

    List<HoldingsProjection> getHoldingsEntities();

    ItemStatusEntity getItemStatusEntity();

    CollectionGroupEntity getCollectionGroupEntity();

    InstitutionEntity getInstitutionEntity();

    List<BibliographicProjection> getBibliographicEntities();
}
