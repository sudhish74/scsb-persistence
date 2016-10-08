package org.recap.repository;

import org.recap.model.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by chenchulakshmig on 3/10/16.
 */
public interface ReportDetailRepository extends JpaRepository<ReportEntity, Integer> {
}
