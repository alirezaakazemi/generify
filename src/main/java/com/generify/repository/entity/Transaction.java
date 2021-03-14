package com.generify.repository.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Transaction {
    private long id;
    private String mobileNumber;
    private String cif;
    private Byte sourceType;
    private String source;
    private Long amount;
    private Byte transactionType;
    private String responseCode;
    private Byte status;
    private Timestamp transactionDate;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String clientIp;
    private String failReason;
    private String displayMessage;
    private String originalFailReason;
    private String originalDisplayMessage;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARSIAN_GENERATOR")
    @SequenceGenerator(name = "PARSIAN_GENERATOR", sequenceName = "TRANSACTION_SEQUENCE", allocationSize = 1)
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
    @Column(name = "SOURCE_TYPE")
    public Byte getSourceType() {
        return sourceType;
    }

    public void setSourceType(Byte sourceType) {
        this.sourceType = sourceType;
    }

    @Basic
    @Column(name = "SOURCE")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "AMOUNT")
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
    @Column(name = "RESPONSE_CODE")
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @Basic
    @Column(name = "STATUS")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "TRANSACTION_DATE")
    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
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
    @Column(name = "MODIFIED_DATE")
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
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
    @Column(name = "FAIL_REASON")
    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    @Basic
    @Column(name = "DISPLAY_MESSAGE")
    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    @Basic
    @Column(name = "ORIGINAL_FAIL_REASON")
    public String getOriginalFailReason() {
        return originalFailReason;
    }

    public void setOriginalFailReason(String originalFailReason) {
        this.originalFailReason = originalFailReason;
    }

    @Basic
    @Column(name = "ORIGINAL_DISPLAY_MESSAGE")
    public String getOriginalDisplayMessage() {
        return originalDisplayMessage;
    }

    public void setOriginalDisplayMessage(String originalDisplayMessage) {
        this.originalDisplayMessage = originalDisplayMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id &&
                transactionType.equals(that.transactionType) &&
                Objects.equals(mobileNumber, that.mobileNumber) &&
                Objects.equals(cif, that.cif) &&
                Objects.equals(sourceType, that.sourceType) &&
                Objects.equals(source, that.source) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(responseCode, that.responseCode) &&
                Objects.equals(status, that.status) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(modifiedDate, that.modifiedDate) &&
                Objects.equals(clientIp, that.clientIp) &&
                Objects.equals(failReason, that.failReason) &&
                Objects.equals(displayMessage, that.displayMessage) &&
                Objects.equals(originalFailReason, that.originalFailReason) &&
                Objects.equals(originalDisplayMessage, that.originalDisplayMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mobileNumber, cif, sourceType, source, amount, transactionType, responseCode, status, transactionDate, createdDate, modifiedDate, clientIp, failReason, displayMessage, originalFailReason, originalDisplayMessage);
    }
}
