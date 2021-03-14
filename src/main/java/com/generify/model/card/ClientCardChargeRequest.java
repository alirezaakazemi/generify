package com.generify.model.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.generify.model.ClientBaseRequest;

public class ClientCardChargeRequest extends ClientBaseRequest implements IClientCardRequest {

    @JsonProperty("tracker_id")
    private String trackerId;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("dest_phone_number")
    private String destPhoneNumber;

    @JsonProperty("service_type")
    private String serviceType;

    private long amount;

    private String pan;

    @JsonProperty("source_deposit")
    private String sourceDeposit;

    public ClientCardChargeRequest() {
    }

    public String getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(String trackerId) {
        this.trackerId = trackerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDestPhoneNumber() {
        return destPhoneNumber;
    }

    public void setDestPhoneNumber(String destPhoneNumber) {
        this.destPhoneNumber = destPhoneNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getSourceDeposit() {
        return sourceDeposit;
    }

    public void setSourceDeposit(String sourceDeposit) {
        this.sourceDeposit = sourceDeposit;
    }
}
