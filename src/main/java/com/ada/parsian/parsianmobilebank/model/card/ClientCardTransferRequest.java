package com.ada.parsian.parsianmobilebank.model.card;

import com.ada.parsian.parsianmobilebank.model.ClientBaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientCardTransferRequest extends ClientBaseRequest implements IClientCardRequest {

    @JsonProperty("tracker_id")
    private String trackerId;

    private long amount;
    private String destination;
    private String pan;
    private String transactionNumber;

    @JsonProperty("destination_type")
    private String destinationType;

    public ClientCardTransferRequest() {
    }

    public String getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(String trackerId) {
        this.trackerId = trackerId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }
}
