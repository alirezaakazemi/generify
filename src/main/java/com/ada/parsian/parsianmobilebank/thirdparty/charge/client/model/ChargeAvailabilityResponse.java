package com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model;

import com.ada.parsian.parsianmobilebank.thirdparty.IThirdPartyResponse;
import com.google.gson.annotations.SerializedName;

public class ChargeAvailabilityResponse implements IThirdPartyResponse {

    private long amount;

    @SerializedName("service_type")
    private int serviceType;

    private byte status;

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("error_message")
    private String errorMessage;

    @SerializedName("en_error_message")
    private String enErrorMessage;

    @SerializedName("error_code")
    private String errorCode;

    public ChargeAvailabilityResponse() {
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getEnErrorMessage() {
        return enErrorMessage;
    }

    public void setEnErrorMessage(String enErrorMessage) {
        this.enErrorMessage = enErrorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
