package org.recap.model;

import javax.persistence.*;

/**
 * Created by pvsubrah on 6/10/16.
 */

@Entity
@Table(name = "bibliographic_holdings_t", schema = "recap", catalog = "")
public class BibliographicHoldingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BIBLIOGRAPHIC_HOLDINGS_ID")
    private Integer bibliographicHoldingsId;

    @Column(name = "BIBLIOGRAPHIC_ID")
    private Integer bibliographicId;

    @Column(name = "HOLDINGS_ID")
    private Integer holdingsId;

    public Integer getBibliographicHoldingsId() {
        return bibliographicHoldingsId;
    }

    public void setBibliographicHoldingsId(Integer bibliographicHoldingsId) {
        this.bibliographicHoldingsId = bibliographicHoldingsId;
    }

    public Integer getBibliographicId() {
        return bibliographicId;
    }

    public void setBibliographicId(Integer bibliographicId) {
        this.bibliographicId = bibliographicId;
    }

    public Integer getHoldingsId() {
        return holdingsId;
    }

    public void setHoldingsId(Integer holdingsId) {
        this.holdingsId = holdingsId;
    }
}
