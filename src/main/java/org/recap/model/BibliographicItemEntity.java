package org.recap.model;

import javax.persistence.*;

/**
 * Created by pvsubrah on 6/10/16.
 */
@Entity
@Table(name = "bibliographic_item_t", schema = "recap", catalog = "")
public class BibliographicItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BIBLIOGRAPHIC_ITEM_ID")
    private Integer bibliographicItemId;

    @Column(name = "BIBLIOGRAPHIC_ID")
    private Integer bibliographicId;

    @Column(name = "ITEM_ID")
    private Integer itemId;

    public Integer getBibliographicItemId() {
        return bibliographicItemId;
    }

    public void setBibliographicItemId(Integer bibliographicItemId) {
        this.bibliographicItemId = bibliographicItemId;
    }

    public Integer getBibliographicId() {
        return bibliographicId;
    }

    public void setBibliographicId(Integer bibliographicId) {
        this.bibliographicId = bibliographicId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
