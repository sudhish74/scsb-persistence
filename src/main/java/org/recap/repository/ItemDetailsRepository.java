package org.recap.repository;

import org.recap.model.ItemEntity;
import org.recap.model.ItemPK;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by chenchulakshmig on 6/13/16.
 */
public interface ItemDetailsRepository extends CrudRepository<ItemEntity, ItemPK> {
    ItemEntity findByOwningInstitutionItemId(String owningInstitutionItemId);
}
