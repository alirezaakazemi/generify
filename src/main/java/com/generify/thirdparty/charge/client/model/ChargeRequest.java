package com.generify.thirdparty.charge.client.model;

import com.generify.thirdparty.IThirdPartyRequest;
import com.google.gson.annotations.SerializedName;

public class ChargeRequest implements IThirdPartyRequest {

    @SerializedName("request_id")
    private String requestId;

    private long transaction_code;

    @SerializedName("mobile_number")
    private String mobileNumber;

    @SerializedName("service_type")
    private int serviceType;

    @SerializedName("product_id")
    private long productId;

    private long amount;

    public ChargeRequest() {
    }

    public ChargeRequest(String requestId, long transaction_code, String mobileNumber, int serviceType, long productId, long amount) {
        this.requestId = requestId;
        this.transaction_code = transaction_code;
        this.mobileNumber = mobileNumber;
        this.serviceType = serviceType;
        this.productId = productId;
        this.amount = amount;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public long getTransaction_code() {
        return transaction_code;
    }

    public void setTransaction_code(long transaction_code) {
        this.transaction_code = transaction_code;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}