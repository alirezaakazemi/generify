package com.ada.parsian.parsianmobilebank.model;

import com.ada.parsian.parsianmobilebank.exception.BaseException;

public class HandledReceivedError<T extends BaseException> {
    private T exception;
    private String receivedResponse;

    public HandledReceivedError(T exception, String receivedResponse) {
        this.exception = exception;
        this.receivedResponse = receivedResponse;
    }

    public HandledReceivedError() {
    }

    public T getException() {
        return exception;
    }

    public void setException(T exception) {
        this.exception = exception;
    }

    public String getReceivedResponse() {
        return receivedResponse;
    }

    public void setReceivedResponse(String receivedResponse) {
        this.receivedResponse = receivedResponse;
    }
}
