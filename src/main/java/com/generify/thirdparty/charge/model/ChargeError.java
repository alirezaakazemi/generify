package com.generify.thirdparty.charge.model;

import com.google.gson.annotations.SerializedName;

public class ChargeError implements IThirdPartyError {

    @SerializedName("error_code")
    private String errorCode;

    @SerializedName("error_message")
    private String errorMessage;

    public ChargeError() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getOriginalDisplayMessage() {
        return null;
    }
}
