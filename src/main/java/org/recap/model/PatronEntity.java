package org.recap.model;

import javax.persistence.*;

/**
 * Created by pvsubrah on 6/11/16.
 */

@Entity
@Table(name = "patron_t", schema = "recap", catalog = "")
public class PatronEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PATRON_ID")
    private Integer patronId;

    @Column(name = "INST_IDENTIFIER")
    private String institutionIdentifier;

    @Column(name = "INST_ID")
    private Integer institutionId;

    @Column(name = "EMAIL_ID")
    private String emailId;

    public Integer getPatronId() {
        return patronId;
    }

    public void setPatronId(Integer patronId) {
        this.patronId = patronId;
    }

    public String getInstitutionIdentifier() {
        return institutionIdentifier;
    }

    public void setInstitutionIdentifier(String institutionIdentifier) {
        this.institutionIdentifier = institutionIdentifier;
    }

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
