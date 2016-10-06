package org.recap.repository;

import org.recap.model.ItemEntity;
import org.recap.model.ItemPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by chenchulakshmig on 6/13/16.
 */
public interface ItemDetailsRepository extends CrudRepository<ItemEntity, ItemPK> {

    ItemEntity findByOwningInstitutionItemId(@Param("owningInstitutionItemId") String owningInstitutionItemId);

    List<ItemEntity> findAllByIsDeletedTrue();

    List<ItemEntity> findAllByIsDeletedFalse();

    long countByIsDeletedTrue();

    long countByIsDeletedFalse();

    List<ItemEntity> findByBarcode(String barcode);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ItemEntity item SET item.isDeleted = true WHERE item.itemId = :itemId")
    int markItemAsDeleted(@Param("itemId") Integer itemId);

    @Query(value = "select itemStatus.statusCode from ItemEntity item, ItemStatusEntity itemStatus where item.itemAvailabilityStatusId = itemStatus.itemStatusId and item.barcode = :barcode and item.isDeleted = 0")
    String getItemStatusByBarcodeAndIsDeletedFalse(@Param("barcode") String barcode);
}
