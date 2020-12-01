package com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model;

import com.ada.parsian.parsianmobilebank.thirdparty.IThirdPartyResponse;
import com.google.gson.annotations.SerializedName;

public class ChargeResponse implements IThirdPartyResponse {

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("mobile_number")
    private String mobileNumber;

    @SerializedName("service_type")
    private byte serviceType;

    private long amount;

    @SerializedName("trace_code")
    private String traceCode;

    private byte status;

    @SerializedName("error_message")
    private String errorMessage;

    @SerializedName("en_error_message")
    private String enErrorMessage;

    @SerializedName("error_code")
    private String errorCode;

    public ChargeResponse() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(byte serviceType) {
        this.serviceType = serviceType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getTraceCode() {
        return traceCode;
    }

    public void setTraceCode(String traceCode) {
        this.traceCode = traceCode;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
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
