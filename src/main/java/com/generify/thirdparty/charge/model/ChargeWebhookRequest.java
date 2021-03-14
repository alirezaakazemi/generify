package com.generify.thirdparty.charge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChargeWebhookRequest {

    @JsonProperty("request_id")
    private long requestId;

    @JsonProperty("transaction_code")
    private String transactionCode;

    public ChargeWebhookRequest() {
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }
}
