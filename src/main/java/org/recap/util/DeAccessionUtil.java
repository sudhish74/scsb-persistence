package org.recap.util;

import org.recap.ReCAPConstants;
import org.recap.model.*;
import org.recap.repository.BibliographicDetailsRepository;
import org.recap.repository.HoldingsDetailsRepository;
import org.recap.repository.ItemDetailsRepository;
import org.recap.repository.ReportDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenchulakshmig on 28/9/16.
 */
@Component
public class DeAccessionUtil {

    @Autowired
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    HoldingsDetailsRepository holdingsDetailsRepository;

    @Autowired
    ItemDetailsRepository itemDetailsRepository;

    @Autowired
    ReportDetailRepository reportDetailRepository;

    @Transactional
    public List<DeAccessionDBResponseEntity> deAccessionItemsInDB(List<String> itemBarcodeList) {
        List<DeAccessionDBResponseEntity> deAccessionDBResponseEntities = new ArrayList<>();
        DeAccessionDBResponseEntity deAccessionDBResponseEntity;
        List<String> responseItemBarcodeList = new ArrayList<>();

        List<ItemEntity> itemEntities = itemDetailsRepository.findByBarcodeIn(itemBarcodeList);
        for (ItemEntity itemEntity : itemEntities) {
            String barcode = itemEntity.getBarcode();
            responseItemBarcodeList.add(barcode);
            if (itemEntity.isDeleted()) {
                deAccessionDBResponseEntity = prepareFailureResponse(barcode, itemEntity, ReCAPConstants.REQUESTED_ITEM_DEACCESSIONED);
                deAccessionDBResponseEntities.add(deAccessionDBResponseEntity);
            } else {
                try {
                    List<Integer> holdingIds = processHoldings(itemEntity.getHoldingsEntities());

                    List<Integer> bibliographicIds = processBibs(itemEntity.getBibliographicEntities());

                    itemDetailsRepository.markItemAsDeleted(itemEntity.getItemId());

                    deAccessionDBResponseEntity = prepareSuccessResponse(barcode, itemEntity, holdingIds, bibliographicIds);
                    deAccessionDBResponseEntities.add(deAccessionDBResponseEntity);
                } catch (Exception ex) {
                    deAccessionDBResponseEntity = prepareFailureResponse(barcode, itemEntity, "Exception " + ex);
                    deAccessionDBResponseEntities.add(deAccessionDBResponseEntity);
                }
            }
        }

        if (responseItemBarcodeList.size() != itemBarcodeList.size()) {
            for (String itemBarcode : itemBarcodeList) {
                if (!responseItemBarcodeList.contains(itemBarcode)) {
                    deAccessionDBResponseEntity = prepareFailureResponse(itemBarcode, null, ReCAPConstants.ITEM_BARCDE_DOESNOT_EXIST);
                    deAccessionDBResponseEntities.add(deAccessionDBResponseEntity);
                }
            }
        }
        return deAccessionDBResponseEntities;
    }

    public List<ReportEntity> processAndSave(List<DeAccessionDBResponseEntity> deAccessionDBResponseEntities) {
        List<ReportEntity> reportEntities = new ArrayList<>();
        ReportEntity reportEntity = null;
        if (!CollectionUtils.isEmpty(deAccessionDBResponseEntities)){
            for (DeAccessionDBResponseEntity deAccessionDBResponseEntity : deAccessionDBResponseEntities){
                List<String> itemOwningInstitutionBibIds = deAccessionDBResponseEntity.getItemOwningInstitutionBibIds();
                if (!CollectionUtils.isEmpty(itemOwningInstitutionBibIds)) {
                    for (String itemOwningInstitutionBibId : itemOwningInstitutionBibIds) {
                        reportEntity = generateReportEntity(deAccessionDBResponseEntity, itemOwningInstitutionBibId);
                        reportEntities.add(reportEntity);
                    }
                } else {
                    reportEntity = generateReportEntity(deAccessionDBResponseEntity, null);
                    reportEntities.add(reportEntity);
                }

            }
            if (!CollectionUtils.isEmpty(reportEntities)) {
                reportDetailRepository.save(reportEntities);
            }
        }
        return reportEntities;
    }

    private ReportEntity generateReportEntity(DeAccessionDBResponseEntity deAccessionDBResponseEntity, String owningInstitutionBibId) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileName("DeAccession_Report");
        reportEntity.setType("DeAccession_Summary_Report");
        reportEntity.setCreatedDate(new Date());

        List<ReportDataEntity> reportDataEntities = new ArrayList<>();

        ReportDataEntity dateReportDataEntity = new ReportDataEntity();
        dateReportDataEntity.setHeaderName(ReCAPConstants.DATE_OF_DEACCESSION);
        dateReportDataEntity.setHeaderValue(formatter.format(new Date()));
        reportDataEntities.add(dateReportDataEntity);

        if (!StringUtils.isEmpty(deAccessionDBResponseEntity.getInstitutionCode())) {
            reportEntity.setInstitutionName(deAccessionDBResponseEntity.getInstitutionCode());

            ReportDataEntity owningInstitutionReportDataEntity = new ReportDataEntity();
            owningInstitutionReportDataEntity.setHeaderName(ReCAPConstants.OWNING_INSTITUTION);
            owningInstitutionReportDataEntity.setHeaderValue(deAccessionDBResponseEntity.getInstitutionCode());
            reportDataEntities.add(owningInstitutionReportDataEntity);
        } else {
            reportEntity.setInstitutionName("NA");
        }

        ReportDataEntity barcodeReportDataEntity = new ReportDataEntity();
        barcodeReportDataEntity.setHeaderName(ReCAPConstants.BARCODE);
        barcodeReportDataEntity.setHeaderValue(deAccessionDBResponseEntity.getBarcode());
        reportDataEntities.add(barcodeReportDataEntity);

        if (!StringUtils.isEmpty(owningInstitutionBibId)) {
            ReportDataEntity owningInstitutionBibIdReportDataEntity = new ReportDataEntity();
            owningInstitutionBibIdReportDataEntity.setHeaderName(ReCAPConstants.OWNING_INSTITUTION_BIB_ID);
            owningInstitutionBibIdReportDataEntity.setHeaderValue(owningInstitutionBibId);
            reportDataEntities.add(owningInstitutionBibIdReportDataEntity);
        }

                /*ReportDataEntity titleReportDataEntity = new ReportDataEntity();
                titleReportDataEntity.setHeaderName(ReCAPConstants.TITLE);
                titleReportDataEntity.setHeaderValue("war");
                reportDataEntities.add(titleReportDataEntity);*/

        if (!StringUtils.isEmpty(deAccessionDBResponseEntity.getCollectionGroupCode())) {
            ReportDataEntity collectionGroupCodeReportDataEntity = new ReportDataEntity();
            collectionGroupCodeReportDataEntity.setHeaderName(ReCAPConstants.COLLECTION_GROUP_CODE);
            collectionGroupCodeReportDataEntity.setHeaderValue(deAccessionDBResponseEntity.getCollectionGroupCode());
            reportDataEntities.add(collectionGroupCodeReportDataEntity);
        }

        ReportDataEntity statusReportDataEntity = new ReportDataEntity();
        statusReportDataEntity.setHeaderName(ReCAPConstants.STATUS);
        statusReportDataEntity.setHeaderValue(deAccessionDBResponseEntity.getStatus());
        reportDataEntities.add(statusReportDataEntity);

        if (!StringUtils.isEmpty(deAccessionDBResponseEntity.getReasonForFailure())) {
            ReportDataEntity reasonForFailureReportDataEntity = new ReportDataEntity();
            reasonForFailureReportDataEntity.setHeaderName(ReCAPConstants.REASON_FOR_FAILURE);
            reasonForFailureReportDataEntity.setHeaderValue(deAccessionDBResponseEntity.getReasonForFailure());
            reportDataEntities.add(reasonForFailureReportDataEntity);
        }

        reportEntity.setReportDataEntities(reportDataEntities);
        return reportEntity;
    }

    private DeAccessionDBResponseEntity prepareSuccessResponse(String itemBarcode, ItemEntity itemEntity, List<Integer> holdingIds, List<Integer> bibliographicIds) {
        DeAccessionDBResponseEntity deAccessionDBResponseEntity;
        deAccessionDBResponseEntity = new DeAccessionDBResponseEntity();
        deAccessionDBResponseEntity.setBarcode(itemBarcode);
        deAccessionDBResponseEntity.setStatus(ReCAPConstants.SUCCESS);
        deAccessionDBResponseEntity.setInstitutionCode(itemEntity.getInstitutionEntity().getInstitutionCode());
        deAccessionDBResponseEntity.setCollectionGroupCode(itemEntity.getCollectionGroupEntity().getCollectionGroupCode());
        List<String> owningInstitutionBibIds = new ArrayList<>();
        List<BibliographicEntity> bibliographicEntities = itemEntity.getBibliographicEntities();
        for (BibliographicEntity bibliographicEntity : bibliographicEntities) {
            owningInstitutionBibIds.add(bibliographicEntity.getOwningInstitutionBibId());
        }
        deAccessionDBResponseEntity.setItemOwningInstitutionBibIds(owningInstitutionBibIds);
        deAccessionDBResponseEntity.setItemId(itemEntity.getItemId());
        deAccessionDBResponseEntity.setBibliographicIds(bibliographicIds);
        deAccessionDBResponseEntity.setHoldingIds(holdingIds);
        return deAccessionDBResponseEntity;
    }

    private DeAccessionDBResponseEntity prepareFailureResponse(String itemBarcode, ItemEntity itemEntity, String reasonForFailure) {
        DeAccessionDBResponseEntity deAccessionDBResponseEntity;
        deAccessionDBResponseEntity = new DeAccessionDBResponseEntity();
        deAccessionDBResponseEntity.setBarcode(itemBarcode);
        deAccessionDBResponseEntity.setStatus(ReCAPConstants.FAILURE);
        deAccessionDBResponseEntity.setReasonForFailure(reasonForFailure);
        if (itemEntity!=null){
            deAccessionDBResponseEntity.setInstitutionCode(itemEntity.getInstitutionEntity().getInstitutionCode());
            deAccessionDBResponseEntity.setCollectionGroupCode(itemEntity.getCollectionGroupEntity().getCollectionGroupCode());
            List<String> owningInstitutionBibIds = new ArrayList<>();
            List<BibliographicEntity> bibliographicEntities = itemEntity.getBibliographicEntities();
            for (BibliographicEntity bibliographicEntity : bibliographicEntities) {
                owningInstitutionBibIds.add(bibliographicEntity.getOwningInstitutionBibId());
            }
            deAccessionDBResponseEntity.setItemOwningInstitutionBibIds(owningInstitutionBibIds);
            deAccessionDBResponseEntity.setItemId(itemEntity.getItemId());
        }
        return deAccessionDBResponseEntity;
    }

    private List<Integer> processBibs(List<BibliographicEntity> bibliographicEntities) {
        List<Integer> bibliographicIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(bibliographicEntities)) {
            for (BibliographicEntity bibliographicEntity : bibliographicEntities) {
                Long nonDeletedItemCount = bibliographicDetailsRepository.getNonDeletedItemsCount(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId());
                if (nonDeletedItemCount == 1) {
                    bibliographicIds.add(bibliographicEntity.getBibliographicId());
                }
            }
            if (!CollectionUtils.isEmpty(bibliographicIds)) {
                bibliographicDetailsRepository.markBibsAsDeleted(bibliographicIds);
            }
        }
        return bibliographicIds;
    }

    private List<Integer> processHoldings(List<HoldingsEntity> holdingsEntities) {
        List<Integer> holdingIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(holdingsEntities)) {
            for (HoldingsEntity holdingsEntity : holdingsEntities) {
                Long nonDeletedItemsCount = holdingsDetailsRepository.getNonDeletedItemsCount(holdingsEntity.getOwningInstitutionId(), holdingsEntity.getOwningInstitutionHoldingsId());
                if (nonDeletedItemsCount == 1) {
                    holdingIds.add(holdingsEntity.getHoldingsId());
                }
            }
            if (!CollectionUtils.isEmpty(holdingIds)) {
                holdingsDetailsRepository.markHoldingsAsDeleted(holdingIds);
            }
        }
        return holdingIds;
    }
}
