package org.recap.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by pvsubrah on 6/10/16.
 */

@Entity
@Table(name = "audit_t", schema = "recap", catalog = "")
public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AUDIT_ID")
    private Integer auditId;

    @Column(name = "TABLE_NAME")
    private String tableName;

    @Column(name = "COLUMN_UPDATED")
    private String columnUpdated;

    @Lob
    @Column(name = "VALUE")
    private String value;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_TIME_UPDATED")
    private Date dateTimeUpdated;

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnUpdated() {
        return columnUpdated;
    }

    public void setColumnUpdated(String columnUpdated) {
        this.columnUpdated = columnUpdated;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDateTimeUpdated() {
        return dateTimeUpdated;
    }

    public void setDateTimeUpdated(Date dateTimeUpdated) {
        this.dateTimeUpdated = dateTimeUpdated;
    }

}
