package org.recap.controller;

import org.recap.model.BibliographicEntity;
import org.recap.model.BibliographicPK;
import org.recap.model.HoldingsEntity;
import org.recap.model.ItemEntity;
import org.recap.repository.BibliographicDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pvsubrah on 6/11/16.
 */

@RestController
@RequestMapping("/bibliographic")
public class BibliographicController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    public BibliographicController(BibliographicDetailsRepository bibliographicDetailsRepository) {
        this.bibliographicDetailsRepository = bibliographicDetailsRepository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(method = RequestMethod.GET, value = "/findOne")
    public BibliographicEntity findOne(Integer owningInstitutionId, String owningInstitutionBibId) {
        BibliographicPK bibliographicPK = new BibliographicPK();
        bibliographicPK.setOwningInstitutionId(owningInstitutionId);
        bibliographicPK.setOwningInstitutionBibId(owningInstitutionBibId);
        return bibliographicDetailsRepository.findOne(bibliographicPK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAll")
    public List<BibliographicEntity> findAll(int pageNum, int numberOfRecords) {
        List<BibliographicEntity> bibliographicEntityList = new ArrayList<>();
        bibliographicEntityList.addAll(bibliographicDetailsRepository.findAll(new PageRequest(pageNum, numberOfRecords)).getContent());
        return bibliographicEntityList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/count")
    public long count() {
        return bibliographicDetailsRepository.count();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    @Transactional
    public Integer create(@RequestBody BibliographicEntity bibliographicEntity) {
        BibliographicEntity savedBibliographicEntity=null;
        BibliographicEntity fetchBibliographicEntity = bibliographicDetailsRepository.findByOwningInstitutionIdAndOwningInstitutionBibId(bibliographicEntity.getOwningInstitutionId(),bibliographicEntity.getOwningInstitutionBibId());
        if(fetchBibliographicEntity ==null) { // New Bib Record

            savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
            entityManager.refresh(savedBibliographicEntity);
        }else{ // Existing bib Record
            // Bib
            fetchBibliographicEntity.setContent(bibliographicEntity.getContent());
            fetchBibliographicEntity.setCreatedBy(bibliographicEntity.getCreatedBy());
            fetchBibliographicEntity.setCreatedDate(bibliographicEntity.getCreatedDate());
            fetchBibliographicEntity.setDeleted(bibliographicEntity.isDeleted());
            fetchBibliographicEntity.setLastUpdatedBy(bibliographicEntity.getLastUpdatedBy());
            fetchBibliographicEntity.setLastUpdatedDate(bibliographicEntity.getLastUpdatedDate());

            // Holding
            List<HoldingsEntity> fetchHoldingsEntities =fetchBibliographicEntity.getHoldingsEntities();
            List<HoldingsEntity> holdingsEntities = new ArrayList<HoldingsEntity>(bibliographicEntity.getHoldingsEntities());

            logger.info("fetchHoldingsEntities = "+fetchHoldingsEntities.size());
            logger.info("holdingsEntities = "+holdingsEntities.size());

            for (Iterator iholdings = holdingsEntities.iterator(); iholdings.hasNext();) {
                HoldingsEntity holdingsEntity =(HoldingsEntity) iholdings.next();
                for (int j=0;j<fetchHoldingsEntities.size();j++) {
                    HoldingsEntity fetchHolding=fetchHoldingsEntities.get(j);
                    if(fetchHolding.getOwningInstitutionHoldingsId().equalsIgnoreCase(holdingsEntity.getOwningInstitutionHoldingsId())  && fetchHolding.getOwningInstitutionId().intValue() == holdingsEntity.getOwningInstitutionId().intValue()) {
                        fetchHolding = copytoHoldingsEntity(fetchHolding,holdingsEntity);
                        iholdings.remove();
                    }
                }
            }
            fetchHoldingsEntities.addAll(holdingsEntities);
            logger.info("Holding Final Count = "+fetchHoldingsEntities.size());

            // Item
            List<ItemEntity> fetchItemsEntities =fetchBibliographicEntity.getItemEntities();
            List<ItemEntity> itemsEntities = new ArrayList<ItemEntity>(bibliographicEntity.getItemEntities());

            logger.info("fetchHoldingsEntities = "+fetchItemsEntities.size());
            logger.info("holdingsEntities = "+itemsEntities.size());

            for (Iterator iItems=itemsEntities.iterator();iItems.hasNext();) {
                ItemEntity itemEntity =(ItemEntity) iItems.next();
                for (Iterator ifetchItems=fetchItemsEntities.iterator();ifetchItems.hasNext();) {
                    ItemEntity fetchItem=(ItemEntity) ifetchItems.next();
                    if(fetchItem.getOwningInstitutionItemId().equalsIgnoreCase(itemEntity.getOwningInstitutionItemId())  && fetchItem.getOwningInstitutionId().intValue() == itemEntity.getOwningInstitutionId().intValue()) {
                        fetchItem = copytoHoldingsEntity(fetchItem,itemEntity);
                        iItems.remove();
                    }
                }
            }
            fetchItemsEntities.addAll(itemsEntities);
            logger.info("Item Final Count = "+fetchItemsEntities.size());

            fetchBibliographicEntity.setHoldingsEntities(fetchHoldingsEntities);
            fetchBibliographicEntity.setItemEntities(fetchItemsEntities);

            savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(fetchBibliographicEntity);
            entityManager.refresh(fetchBibliographicEntity);
        }
        return savedBibliographicEntity.getBibliographicId();
    }

    private HoldingsEntity copytoHoldingsEntity(HoldingsEntity fetchHoldingsEntity,HoldingsEntity holdingsEntity){
        fetchHoldingsEntity.setContent(holdingsEntity.getContent()); ;
        fetchHoldingsEntity.setCreatedBy(holdingsEntity.getCreatedBy());
        fetchHoldingsEntity.setCreatedDate(holdingsEntity.getCreatedDate());
        fetchHoldingsEntity.setDeleted(holdingsEntity.isDeleted());
        fetchHoldingsEntity.setLastUpdatedBy(holdingsEntity.getLastUpdatedBy());
        fetchHoldingsEntity.setLastUpdatedDate(holdingsEntity.getLastUpdatedDate());
        return fetchHoldingsEntity;
    }

    private ItemEntity copytoHoldingsEntity(ItemEntity fetchItemEntity,ItemEntity itemEntity){
        fetchItemEntity.setBarcode(itemEntity.getBarcode()); ;
        fetchItemEntity.setCreatedBy(itemEntity.getCreatedBy());
        fetchItemEntity.setCreatedDate(itemEntity.getCreatedDate());
        fetchItemEntity.setDeleted(itemEntity.isDeleted());
        fetchItemEntity.setLastUpdatedBy(itemEntity.getLastUpdatedBy());
        fetchItemEntity.setLastUpdatedDate(itemEntity.getLastUpdatedDate());
        fetchItemEntity.setCallNumber(itemEntity.getCallNumber());
        fetchItemEntity.setCustomerCode(itemEntity.getCustomerCode());
        fetchItemEntity.setCallNumberType(itemEntity.getCallNumberType());
        fetchItemEntity.setItemAvailabilityStatusId(itemEntity.getItemAvailabilityStatusId());
        fetchItemEntity.setCopyNumber(itemEntity.getCopyNumber());
        fetchItemEntity.setCollectionGroupId(itemEntity.getCollectionGroupId());
        fetchItemEntity.setUseRestrictions(itemEntity.getUseRestrictions());
        fetchItemEntity.setVolumePartYear(itemEntity.getVolumePartYear());
        return fetchItemEntity;
    }
}
