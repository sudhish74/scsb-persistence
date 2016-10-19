package org.recap.Projection;

import org.recap.model.CollectionGroupEntity;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by chenchulakshmig on 17/10/16.
 */
@Projection(name = "collectionGroupProjection", types = CollectionGroupEntity.class)
public interface CollectionGroupProjection {

    Integer getCollectionGroupId();

    String getCollectionGroupCode();

    String getCollectionGroupDescription();
}
