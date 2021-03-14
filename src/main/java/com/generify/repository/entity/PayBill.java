package com.generify.repository.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PAY_BILL", schema = "DEVELOP_IDENTITY", catalog = "")
public class PayBill {
    private long transactionId;
    private String billId;
    private String payId;
    private String billType;

    @Id
    @Column(name = "TRANSACTION_ID")
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "BILL_ID")
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    @Basic
    @Column(name = "PAY_ID")
    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    @Basic
    @Column(name = "BILL_TYPE")
    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayBill payBill = (PayBill) o;
        return transactionId == payBill.transactionId &&
                Objects.equals(billId, payBill.billId) &&
                Objects.equals(payId, payBill.payId) &&
                Objects.equals(billType, payBill.billType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, billId, payId, billType);
    }
}
