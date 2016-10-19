package org.recap.Projection;

import org.recap.model.ItemStatusEntity;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by chenchulakshmig on 17/10/16.
 */
@Projection(name = "itemStatusProjection", types = ItemStatusEntity.class)
public interface ItemStatusProjection {
    Integer getItemStatusId();

    String getStatusCode();

    String getStatusDescription();
}
