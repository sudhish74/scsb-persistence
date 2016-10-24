package org.recap.repository;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.ReportDataEntity;
import org.recap.model.ReportEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by chenchulakshmig on 24/10/16.
 */
public class ReportDetailRepositoryUT extends BaseTestCase{

    @Autowired
    ReportDetailRepository reportDetailRepository;

    @Test
    public void testFindByTypeAndDateRange() throws Exception {
        ReportEntity reportEntity = saveReportEntity();
        Calendar cal = Calendar.getInstance();
        Date from = reportEntity.getCreatedDate();
        cal.setTime(from);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        from = cal.getTime();
        Date to = reportEntity.getCreatedDate();
        cal.setTime(to);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        to = cal.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");

        List<ReportEntity> reportEntities = reportDetailRepository.findByTypeAndDateRange(reportEntity.getType(), simpleDateFormat.format(from), simpleDateFormat.format(to));
        assertNotNull(reportEntities);
        assertNotNull(reportEntities.get(0));
    }

    private ReportEntity saveReportEntity() {
        List<ReportDataEntity> reportDataEntities = new ArrayList<>();

        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileName("test.xml");
        reportEntity.setCreatedDate(new Date());
        reportEntity.setType("Failure");
        reportEntity.setInstitutionName("CUL");

        ReportDataEntity reportDataEntity = new ReportDataEntity();
        reportDataEntity.setHeaderName("Barcode");
        reportDataEntity.setHeaderValue("103");
        reportDataEntities.add(reportDataEntity);

        ReportDataEntity reportDataEntity2 = new ReportDataEntity();
        reportDataEntity2.setHeaderName("CallNumber");
        reportDataEntity2.setHeaderValue("X123");
        reportDataEntities.add(reportDataEntity2);

        ReportDataEntity reportDataEntity3 = new ReportDataEntity();
        reportDataEntity3.setHeaderName("ItemId");
        reportDataEntity3.setHeaderValue("10412");
        reportDataEntities.add(reportDataEntity3);

        ReportDataEntity reportDataEntity4 = new ReportDataEntity();
        reportDataEntity4.setHeaderName("Institution");
        reportDataEntity4.setHeaderValue("CUL");
        reportDataEntities.add(reportDataEntity4);

        reportEntity.setReportDataEntities(reportDataEntities);

        return reportDetailRepository.save(reportEntity);
    }

}