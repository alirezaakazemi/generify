package com.ada.parsian.parsianmobilebank.exception;

import com.ada.parsian.parsianmobilebank.model.ErrorCodes;

import java.util.ArrayList;

public class DuplicateTransactionException extends BaseException {

    public DuplicateTransactionException(String displayMessage, ArrayList<String> errorMessages) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.DUPLICATE_TRANSACTION);
        error.setDisplayMessage(displayMessage);
        error.setErrorMessages(errorMessages);
    }
}