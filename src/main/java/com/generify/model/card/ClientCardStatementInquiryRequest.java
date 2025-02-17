package com.generify.model.card;

import com.generify.model.ClientBaseRequest;

public class ClientCardStatementInquiryRequest extends ClientBaseRequest implements IClientCardRequest {

    private String depositNumber;
    private String pan;

    public ClientCardStatementInquiryRequest() {
    }

    public String getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}
