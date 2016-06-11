package org.recap.model;

import javax.persistence.*;

/**
 * Created by pvsubrah on 6/11/16.
 */

@Entity
@Table(name = "notes_t", schema = "recap", catalog = "")
public class NotesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NOTES_ID")
    private Integer notesId;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "ITEM_ID")
    private Integer itemId;

    @Column(name = "REQUEST_ID")
    private Integer requestId;

    public Integer getNotesId() {
        return notesId;
    }

    public void setNotesId(Integer notesId) {
        this.notesId = notesId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }
}
