package com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model;

import com.ada.parsian.parsianmobilebank.thirdparty.IThirdPartyRequest;
import com.google.gson.annotations.SerializedName;

public class ChargeAvailabilityRequest implements IThirdPartyRequest {

    @SerializedName("service_type")
    private int serviceType;

    @SerializedName("product_id")
    private long productId;

    private long amount;

    @SerializedName("dest_phone_number")
    private String destPhoneNumber;

    public ChargeAvailabilityRequest() {
    }

    public ChargeAvailabilityRequest(int serviceType, long productIid, long amount, String mobileNumber) {
        this.serviceType = serviceType;
        this.productId = productIid;
        this.amount = amount;
        this.destPhoneNumber = mobileNumber;
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

    public String getDestPhoneNumber() {
        return destPhoneNumber;
    }

    public void setDestPhoneNumber(String destPhoneNumber) {
        this.destPhoneNumber = destPhoneNumber;
    }

}
