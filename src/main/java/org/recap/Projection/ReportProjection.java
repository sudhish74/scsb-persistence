package org.recap.Projection;

import org.recap.model.ItemEntity;
import org.recap.model.ReportDataEntity;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by chenchulakshmig on 13/10/16.
 */
@Projection(name = "reportProjection", types = ItemEntity.class)
public interface ReportProjection {
    Integer getRecordNumber();

    String getFileName();

    String getType();

    Date getCreatedDate();

    String getInstitutionName();

    List<ReportDataEntity> getReportDataEntities();
}
