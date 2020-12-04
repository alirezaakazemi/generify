package com.ada.parsian.parsianmobilebank.repository.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "TRANSACTION_LOG", schema = "DEVELOP_IDENTITY", catalog = "")
public class TransactionLog {
    private long id;
    private String mobileNumber;
    private String cif;
    private Byte transactionType;
    private Byte sourceType;
    private Timestamp createdDate;
    private String clientIp;
    private Byte type;
    private String headers;
    private String body;
    private long transactionId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARSIAN_GENERATOR")
    @SequenceGenerator(name = "PARSIAN_GENERATOR", sequenceName = "TRANSACTION_LOG_SEQUENCE", allocationSize = 1)
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MOBILE_NUMBER")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Basic
    @Column(name = "CIF")
    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    @Basic
    @Column(name = "TRANSACTION_TYPE")
    public Byte getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Byte transactionType) {
        this.transactionType = transactionType;
    }

    @Basic
    @Column(name = "SOURCE_TYPE")
    public Byte getSourceType() {
        return sourceType;
    }

    public void setSourceType(Byte sourceType) {
        this.sourceType = sourceType;
    }

    @Basic
    @Column(name = "CREATED_DATE")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "CLIENT_IP")
    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Basic
    @Column(name = "TYPE")
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "HEADERS")
    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    @Basic
    @Column(name = "BODY")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Basic
    @Column(name = "TRANSACTION_ID")
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionLog that = (TransactionLog) o;
        return id == that.id &&
                transactionType == that.transactionType &&
                sourceType == that.sourceType &&
                transactionId == that.transactionId &&
                Objects.equals(mobileNumber, that.mobileNumber) &&
                Objects.equals(cif, that.cif) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(clientIp, that.clientIp) &&
                Objects.equals(type, that.type) &&
                Objects.equals(headers, that.headers) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mobileNumber, cif, transactionType, sourceType, createdDate, clientIp, type, headers, body, transactionId);
    }
}
