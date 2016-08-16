package org.recap.model;

import java.io.Serializable;

/**
 * Created by hemalathas on 22/7/16.
 */
public class BibliographicPK implements Serializable {
    private Integer owningInstitutionId;
    private String owningInstitutionBibId;

    public BibliographicPK() {
        //empty constructor
    }

    public BibliographicPK(Integer owningInstitutionId, String owningInstitutionBibId) {
        this.owningInstitutionId = owningInstitutionId;
        this.owningInstitutionBibId = owningInstitutionBibId;
    }

    public Integer getOwningInstitutionId() {
        return owningInstitutionId;
    }

    public void setOwningInstitutionId(Integer owningInstitutionId) {
        this.owningInstitutionId = owningInstitutionId;
    }

    public String getOwningInstitutionBibId() {
        return owningInstitutionBibId;
    }

    public void setOwningInstitutionBibId(String owningInstitutionBibId) {
        this.owningInstitutionBibId = owningInstitutionBibId;
    }



    @Override
    public int hashCode() {
        return Integer.valueOf(owningInstitutionId.toString()+owningInstitutionBibId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        BibliographicPK bibliographicPK  = (BibliographicPK) obj;
        if(bibliographicPK.getOwningInstitutionId().equals(owningInstitutionId) && bibliographicPK.getOwningInstitutionBibId().equals(owningInstitutionBibId)){
            return true;
        }

        return false;
    }
}
