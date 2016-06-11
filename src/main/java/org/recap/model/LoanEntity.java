package org.recap.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by pvsubrah on 6/11/16.
 */

@Entity
@Table(name = "loan_t", schema = "recap", catalog = "")
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOAN_ID")
    private Integer loanId;

    @Column(name = "ITEM_ID")
    private Integer itemId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOAN_DUE_DATE")
    private Date loanDueDate;

    @Column(name = "PATRON_ID")
    private Integer patronId;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Date getLoanDueDate() {
        return loanDueDate;
    }

    public void setLoanDueDate(Date loanDueDate) {
        this.loanDueDate = loanDueDate;
    }

    public Integer getPatronId() {
        return patronId;
    }

    public void setPatronId(Integer patronId) {
        this.patronId = patronId;
    }
}
