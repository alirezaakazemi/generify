package com.generify.exception;

import com.google.gson.annotations.SerializedName;

public class BankError {

    private String timestamp;
    private int code;
    private String message;
    private String errorDetails;
    private String exceptionDetail;
    private Description description;

    public BankError() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public static class Description {

        @SerializedName("en_US")
        private String enMessage;

        @SerializedName("fa_IR")
        private String message;

        public Description() {
        }

        public String getEnMessage() {
            return enMessage;
        }

        public void setEnMessage(String enMessage) {
            this.enMessage = enMessage;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
