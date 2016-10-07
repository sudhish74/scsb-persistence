package org.recap.controller;

import org.recap.model.DeAccessionDBResponseEntity;
import org.recap.model.ReportEntity;
import org.recap.util.DeAccessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chenchulakshmig on 7/10/16.
 */
@RestController
@RequestMapping("/deAccession")
public class DeAccessionController {

    @Autowired
    DeAccessionUtil deAccessionUtil;

    @RequestMapping(method = RequestMethod.GET, value = "/deAccessionItemsInDBAndSaveReports")
    public List<DeAccessionDBResponseEntity> deAccessionItemsInDBAndSaveReports(String barcodes) {
        List<String> itemBarcodeList = Arrays.asList(barcodes.split(","));
        List<DeAccessionDBResponseEntity> deAccessionDBResponseEntities = deAccessionUtil.deAccessionItemsInDB(itemBarcodeList);
        deAccessionUtil.processAndSave(deAccessionDBResponseEntities);
        return deAccessionDBResponseEntities;
    }

}
