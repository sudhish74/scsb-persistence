package org.recap.repository;

import org.recap.Projection.ItemProjection;
import org.recap.model.ItemEntity;
import org.recap.model.ItemPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenchulakshmig on 6/13/16.
 */
@RepositoryRestResource(collectionResourceRel = "item", path = "item", excerptProjection = ItemProjection.class)
public interface ItemDetailsRepository extends PagingAndSortingRepository<ItemEntity, ItemPK> {

    ItemEntity findByOwningInstitutionItemId(@Param("owningInstitutionItemId") String owningInstitutionItemId);

    List<ItemEntity> findAllByIsDeletedTrue();

    List<ItemEntity> findAllByIsDeletedFalse();

    long countByIsDeletedTrue();

    long countByIsDeletedFalse();

    List<ItemEntity> findByBarcode(@Param("barcode") String barcode);

    List<ItemEntity> findByBarcodeIn(@Param("barcodes") List<String> barcodes);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE ItemEntity item SET item.isDeleted = true WHERE item.itemId = :itemId")
    int markItemAsDeleted(@Param("itemId") Integer itemId);

    @Query(value = "select itemStatus.statusCode from ItemEntity item, ItemStatusEntity itemStatus where item.itemAvailabilityStatusId = itemStatus.itemStatusId and item.barcode = :barcode and item.isDeleted = 0")
    String getItemStatusByBarcodeAndIsDeletedFalse(@Param("barcode") String barcode);
}
