package org.recap.controller;

import org.recap.model.ReportEntity;
import org.recap.repository.ReportDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by chenchulakshmig on 11/10/16.
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportDetailRepository reportDetailRepository;

    @Autowired
    public ReportController(ReportDetailRepository reportDetailRepository) {
        this.reportDetailRepository = reportDetailRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public Iterable<ReportEntity> create(@RequestBody List<ReportEntity> reportEntities) {
        return reportDetailRepository.save(reportEntities);
    }
}
