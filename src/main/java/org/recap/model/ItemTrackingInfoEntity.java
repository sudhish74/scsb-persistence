package org.recap.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by pvsubrah on 6/11/16.
 */

@Entity
@Table(name = "item_tracking_info_t", schema = "recap", catalog = "")
public class ItemTrackingInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRACKING_INFO_ID")
    private Integer trackingInfoId;

    @Column(name = "TRACKING_STATUS_ID")
    private Integer trackingStatusId;

    @Column(name = "BIN_NUMBER")
    private String billNumber;

    @Column(name = "ITEM_ID")
    private Integer itemId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATETIME")
    private Date updatedDateTime;

    public Integer getTrackingInfoId() {
        return trackingInfoId;
    }

    public void setTrackingInfoId(Integer trackingInfoId) {
        this.trackingInfoId = trackingInfoId;
    }

    public Integer getTrackingStatusId() {
        return trackingStatusId;
    }

    public void setTrackingStatusId(Integer trackingStatusId) {
        this.trackingStatusId = trackingStatusId;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }
}
