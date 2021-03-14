package com.generify.model.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.generify.model.ClientBaseRequest;

public class ClientCardPayBillRequest extends ClientBaseRequest implements IClientCardRequest {

    @JsonProperty("tracker_id")
    private String trackerId;

    private String pan;

    @JsonProperty("bill_id")
    private String billId;

    @JsonProperty("pay_id")
    private String payId;

    public ClientCardPayBillRequest() {
    }

    public String getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(String trackerId) {
        this.trackerId = trackerId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }
}
