package org.recap.repository;

import org.recap.Projection.ReportProjection;
import org.recap.model.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by chenchulakshmig on 3/10/16.
 */
@RepositoryRestResource(collectionResourceRel = "report", path = "report", excerptProjection = ReportProjection.class)
public interface ReportDetailRepository extends JpaRepository<ReportEntity, Integer> {

    @Query(value = "select * from report_t where TYPE=?1 and CREATED_DATE >= ?2 and CREATED_DATE <= ?3", nativeQuery = true)
    List<ReportEntity> findByTypeAndDateRange(@Param("type") String type, @Param("from")  String from,@Param("to")  String to);
}
