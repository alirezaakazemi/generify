package com.ada.parsian.parsianmobilebank.repository.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CARD_TRANSFER", schema = "DEVELOP_IDENTITY", catalog = "")
public class CardTransfer {
    private long transactionId;
    private String destination;
    private Byte destinationType;

    @Id
    @Column(name = "TRANSACTION_ID")
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "DESTINATION")
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Basic
    @Column(name = "DESTINATION_TYPE")
    public Byte getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(Byte destinationType) {
        this.destinationType = destinationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardTransfer that = (CardTransfer) o;
        return transactionId == that.transactionId &&
                destinationType == that.destinationType &&
                Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, destination, destinationType);
    }
}
