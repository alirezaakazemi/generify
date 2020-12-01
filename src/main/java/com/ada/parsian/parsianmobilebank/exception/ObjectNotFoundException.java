package com.ada.parsian.parsianmobilebank.exception;

import com.ada.parsian.parsianmobilebank.model.ErrorCodes;

import java.util.ArrayList;

public class ObjectNotFoundException extends BaseException {

    public ObjectNotFoundException(String displayMessage, ArrayList<String> errorMessages) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.OBJECT_NOT_FOUND);
        error.setDisplayMessage(displayMessage);
        error.setErrorMessages(errorMessages);
    }
}
