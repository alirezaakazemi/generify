package com.ada.parsian.parsianmobilebank.exception;

import com.ada.parsian.parsianmobilebank.model.Constant;
import com.ada.parsian.parsianmobilebank.model.ErrorCodes;
import org.springframework.context.MessageSource;

import java.util.ArrayList;

public class InvalidInputDataException extends BaseException {

    public InvalidInputDataException(MessageSource messageSource) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.INVALID_INPUT_DATA);
        error.setDisplayMessage(getMessage(messageSource, "InvalidInputData"));
        error.setErrorMessages(new ArrayList<String>() {{
            add(Constant.INVALID_INPUT_DATA);
        }});
    }
}
