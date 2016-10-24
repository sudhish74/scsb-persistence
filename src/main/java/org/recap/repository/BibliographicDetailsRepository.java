package org.recap.repository;

import org.recap.model.BibliographicEntity;
import org.recap.model.BibliographicPK;
import org.recap.model.HoldingsEntity;
import org.recap.model.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pvsubrah on 6/10/16.
 */
@RepositoryRestResource(collectionResourceRel = "bibliographic", path = "bibliographic")
public interface BibliographicDetailsRepository extends JpaRepository<BibliographicEntity, BibliographicPK> {

    BibliographicEntity findByBibliographicId(Integer bibliographicId);

    BibliographicEntity findByOwningInstitutionIdAndOwningInstitutionBibId(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionBibId") String owningInstitutionBibId);

    @Query(value = "SELECT COUNT(*) FROM ITEM_T, BIBLIOGRAPHIC_ITEM_T WHERE BIBLIOGRAPHIC_ITEM_T.ITEM_INST_ID = ITEM_T.OWNING_INST_ID " +
            "AND BIBLIOGRAPHIC_ITEM_T.OWNING_INST_ITEM_ID = ITEM_T.OWNING_INST_ITEM_ID AND ITEM_T.IS_DELETED = 0 AND " +
            "BIBLIOGRAPHIC_ITEM_T.OWNING_INST_BIB_ID = :owningInstitutionBibId AND BIBLIOGRAPHIC_ITEM_T.BIB_INST_ID = :owningInstitutionId", nativeQuery = true)
    Long getNonDeletedItemsCount(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionBibId") String owningInstitutionBibId);

    List<ItemEntity> getNonDeletedItemEntities(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionBibId") String owningInstitutionBibId);

    @Query(value = "SELECT COUNT(*) FROM ITEM_T, BIBLIOGRAPHIC_ITEM_T WHERE BIBLIOGRAPHIC_ITEM_T.ITEM_INST_ID = ITEM_T.OWNING_INST_ID " +
            "AND BIBLIOGRAPHIC_ITEM_T.OWNING_INST_ITEM_ID = ITEM_T.OWNING_INST_ITEM_ID AND ITEM_T.IS_DELETED != 0 AND " +
            "BIBLIOGRAPHIC_ITEM_T.OWNING_INST_BIB_ID = :owningInstitutionBibId AND BIBLIOGRAPHIC_ITEM_T.BIB_INST_ID = :owningInstitutionId", nativeQuery = true)
    Long getDeletedItemsCount(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionBibId") String owningInstitutionBibId);

    List<ItemEntity> getDeletedItemEntities(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionBibId") String owningInstitutionBibId);

    @Query(value = "SELECT COUNT(*) FROM HOLDINGS_T, BIBLIOGRAPHIC_HOLDINGS_T WHERE BIBLIOGRAPHIC_HOLDINGS_T.HOLDINGS_INST_ID = HOLDINGS_T.OWNING_INST_ID " +
            "AND BIBLIOGRAPHIC_HOLDINGS_T.OWNING_INST_HOLDINGS_ID = HOLDINGS_T.OWNING_INST_HOLDINGS_ID AND HOLDINGS_T.IS_DELETED = 0 AND " +
            "BIBLIOGRAPHIC_HOLDINGS_T.OWNING_INST_BIB_ID = :owningInstitutionBibId AND BIBLIOGRAPHIC_HOLDINGS_T.BIB_INST_ID = :owningInstitutionId", nativeQuery = true)
    Long getNonDeletedHoldingsCount(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionBibId") String owningInstitutionBibId);

    @Query(value = "SELECT HOLDINGS_T.* FROM HOLDINGS_T, BIBLIOGRAPHIC_HOLDINGS_T WHERE BIBLIOGRAPHIC_HOLDINGS_T.HOLDINGS_INST_ID = HOLDINGS_T.OWNING_INST_ID " +
            "AND BIBLIOGRAPHIC_HOLDINGS_T.OWNING_INST_HOLDINGS_ID = HOLDINGS_T.OWNING_INST_HOLDINGS_ID AND HOLDINGS_T.IS_DELETED = 0 AND " +
            "BIBLIOGRAPHIC_HOLDINGS_T.OWNING_INST_BIB_ID = :owningInstitutionBibId AND BIBLIOGRAPHIC_HOLDINGS_T.BIB_INST_ID = :owningInstitutionId", nativeQuery = true)
    List<HoldingsEntity> getNonDeletedHoldingsEntities(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionBibId") String owningInstitutionBibId);

    @Query(value = "SELECT COUNT(*) FROM HOLDINGS_T, BIBLIOGRAPHIC_HOLDINGS_T WHERE BIBLIOGRAPHIC_HOLDINGS_T.HOLDINGS_INST_ID = HOLDINGS_T.OWNING_INST_ID " +
            "AND BIBLIOGRAPHIC_HOLDINGS_T.OWNING_INST_HOLDINGS_ID = HOLDINGS_T.OWNING_INST_HOLDINGS_ID AND HOLDINGS_T.IS_DELETED != 0 AND " +
            "BIBLIOGRAPHIC_HOLDINGS_T.OWNING_INST_BIB_ID = :owningInstitutionBibId AND BIBLIOGRAPHIC_HOLDINGS_T.BIB_INST_ID = :owningInstitutionId", nativeQuery = true)
    Long getDeletedHoldingsCount(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionBibId") String owningInstitutionBibId);

    @Query(value = "SELECT HOLDINGS_T.* FROM HOLDINGS_T, BIBLIOGRAPHIC_HOLDINGS_T WHERE BIBLIOGRAPHIC_HOLDINGS_T.HOLDINGS_INST_ID = HOLDINGS_T.OWNING_INST_ID " +
            "AND BIBLIOGRAPHIC_HOLDINGS_T.OWNING_INST_HOLDINGS_ID = HOLDINGS_T.OWNING_INST_HOLDINGS_ID AND HOLDINGS_T.IS_DELETED != 0 AND " +
            "BIBLIOGRAPHIC_HOLDINGS_T.OWNING_INST_BIB_ID = :owningInstitutionBibId AND BIBLIOGRAPHIC_HOLDINGS_T.BIB_INST_ID = :owningInstitutionId", nativeQuery = true)
    List<HoldingsEntity> getDeletedHoldingsEntities(@Param("owningInstitutionId") Integer owningInstitutionId, @Param("owningInstitutionBibId") String owningInstitutionBibId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE BibliographicEntity bib SET bib.isDeleted = true WHERE bib.bibliographicId IN :bibliographicIds")
    int markBibsAsDeleted(@Param("bibliographicIds") List<Integer> bibliographicIds);
}
