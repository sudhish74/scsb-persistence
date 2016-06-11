package org.recap.model;

import javax.persistence.*;

/**
 * Created by pvsubrah on 6/11/16.
 */

@Entity
@Table(name = "request_type_t", schema = "recap", catalog = "")
public class RequestTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "REQUEST_TYPE_ID")
    private Integer requestTypeId;

    @Column(name = "REQUEST_TYPE_CODE")
    private String requestTypeCode;

    @Column(name = "REQUEST_TYPE_DESC")
    private String requestTypeDescription;

    public Integer getRequestTypeId() {
        return requestTypeId;
    }

    public void setRequestTypeId(Integer requestTypeId) {
        this.requestTypeId = requestTypeId;
    }

    public String getRequestTypeCode() {
        return requestTypeCode;
    }

    public void setRequestTypeCode(String requestTypeCode) {
        this.requestTypeCode = requestTypeCode;
    }

    public String getRequestTypeDescription() {
        return requestTypeDescription;
    }

    public void setRequestTypeDescription(String requestTypeDescription) {
        this.requestTypeDescription = requestTypeDescription;
    }
}
