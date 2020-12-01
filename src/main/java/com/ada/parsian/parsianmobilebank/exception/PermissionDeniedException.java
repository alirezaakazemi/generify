package com.ada.parsian.parsianmobilebank.exception;

import com.ada.parsian.parsianmobilebank.model.ErrorCodes;
import org.springframework.context.MessageSource;

import java.util.ArrayList;

public class PermissionDeniedException extends BaseException {

    public PermissionDeniedException(MessageSource messageSource, ArrayList<String> errorMessages) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.PERMISSION_DENIED_EXCEPTION);
        error.setDisplayMessage(getMessage(messageSource, "PermissionDeniedException"));
        error.setErrorMessages(errorMessages);
    }
}
