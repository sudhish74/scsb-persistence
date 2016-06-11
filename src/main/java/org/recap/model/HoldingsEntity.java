package org.recap.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by pvsubrah on 6/11/16.
 */

@Entity
@Table(name = "holdings_t", schema = "recap", catalog = "")
public class HoldingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HOLDINGS_ID")
    private Integer holdingsId;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "BIBLIOGRAPHIC_ID")
    private Integer bibliographicId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    @Column(name = "OWNING_INST_HOLDINGS_ID")
    private String owningInstitutionHoldingsId;

    public Integer getHoldingsId() {
        return holdingsId;
    }

    public void setHoldingsId(Integer holdingsId) {
        this.holdingsId = holdingsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getBibliographicId() {
        return bibliographicId;
    }

    public void setBibliographicId(Integer bibliographicId) {
        this.bibliographicId = bibliographicId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getOwningInstitutionHoldingsId() {
        return owningInstitutionHoldingsId;
    }

    public void setOwningInstitutionHoldingsId(String owningInstitutionHoldingsId) {
        this.owningInstitutionHoldingsId = owningInstitutionHoldingsId;
    }
}
