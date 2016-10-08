package org.recap.repository;

import org.recap.model.HoldingsEntity;
import org.recap.model.HoldingsPK;
import org.recap.model.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenchulakshmig on 6/13/16.
 */
@RepositoryRestResource(collectionResourceRel = "holdings", path = "holdings")
public interface HoldingsDetailsRepository extends JpaRepository<HoldingsEntity, HoldingsPK> {

    HoldingsEntity findByHoldingsId(Integer holdingsId);

    @Query(value = "SELECT COUNT(*) FROM ITEM_T, ITEM_HOLDINGS_T WHERE ITEM_HOLDINGS_T.ITEM_INST_ID = ITEM_T.OWNING_INST_ID AND " +
            "ITEM_HOLDINGS_T.OWNING_INST_ITEM_ID = ITEM_T.OWNING_INST_ITEM_ID AND ITEM_T.IS_DELETED = 0 AND " +
            "ITEM_HOLDINGS_T.OWNING_INST_HOLDINGS_ID = :owningInstitutionHoldingsId AND ITEM_HOLDINGS_T.HOLDINGS_INST_ID = :owningInstitutionId", nativeQuery = true)
    Long getNonDeletedItemsCount(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionHoldingsId") String owningInstitutionHoldingsId);

    List<ItemEntity> getNonDeletedItemEntities(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionHoldingsId") String owningInstitutionHoldingsId);

    @Query(value = "SELECT COUNT(*) FROM ITEM_T, ITEM_HOLDINGS_T WHERE ITEM_HOLDINGS_T.ITEM_INST_ID = ITEM_T.OWNING_INST_ID AND " +
            "ITEM_HOLDINGS_T.OWNING_INST_ITEM_ID = ITEM_T.OWNING_INST_ITEM_ID AND ITEM_T.IS_DELETED != 0 AND " +
            "ITEM_HOLDINGS_T.OWNING_INST_HOLDINGS_ID = :owningInstitutionHoldingsId AND ITEM_HOLDINGS_T.HOLDINGS_INST_ID = :owningInstitutionId", nativeQuery = true)
    Long getDeletedItemsCount(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionHoldingsId") String owningInstitutionHoldingsId);

    List<ItemEntity> getDeletedItemEntities(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionHoldingsId") String owningInstitutionHoldingsId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE HoldingsEntity holdings SET holdings.isDeleted = true WHERE holdings.holdingsId IN :holdingIds")
    int markHoldingsAsDeleted(@Param("holdingIds") List<Integer> holdingIds);
}
