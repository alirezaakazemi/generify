package com.ada.parsian.parsianmobilebank.repository.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Charge {
    private long transactionId;
    private String requestId;
    private long productId;
    private String destinationMobile;
    private long chargeStatus;
    private Long reserveStatus;
    private Long reverseStatus;
    private Long paymentStatus;
    private Boolean refunded;

    @Id
    @Column(name = "TRANSACTION_ID")
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "REQUEST_ID")
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Basic
    @Column(name = "PRODUCT_ID")
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "DESTINATION_MOBILE")
    public String getDestinationMobile() {
        return destinationMobile;
    }

    public void setDestinationMobile(String destinationMobile) {
        this.destinationMobile = destinationMobile;
    }

    @Basic
    @Column(name = "CHARGE_STATUS")
    public long getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(long chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    @Basic
    @Column(name = "RESERVE_STATUS")
    public Long getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(Long reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    @Basic
    @Column(name = "REVERSE_STATUS")
    public Long getReverseStatus() {
        return reverseStatus;
    }

    public void setReverseStatus(Long reverseStatus) {
        this.reverseStatus = reverseStatus;
    }

    @Basic
    @Column(name = "PAYMENT_STATUS")
    public Long getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Long paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Basic
    @Column(name = "REFUNDED")
    public Boolean getRefunded() {
        return refunded;
    }

    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charge charge = (Charge) o;
        return transactionId == charge.transactionId &&
                productId == charge.productId &&
                chargeStatus == charge.chargeStatus &&
                Objects.equals(requestId, charge.requestId) &&
                Objects.equals(destinationMobile, charge.destinationMobile) &&
                Objects.equals(reserveStatus, charge.reserveStatus) &&
                Objects.equals(reverseStatus, charge.reverseStatus) &&
                Objects.equals(paymentStatus, charge.paymentStatus) &&
                Objects.equals(refunded, charge.refunded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, requestId, productId, destinationMobile, chargeStatus, reserveStatus, reverseStatus, paymentStatus, refunded);
    }
}
