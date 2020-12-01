package com.ada.parsian.parsianmobilebank.exception;

import com.ada.parsian.parsianmobilebank.model.Constant;
import com.ada.parsian.parsianmobilebank.model.ErrorCodes;
import org.springframework.context.MessageSource;

import java.util.ArrayList;

public class InputFormatException extends BaseException {

    public InputFormatException(MessageSource messageSource) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.INPUT_FORMAT_EXCEPTION);
        error.setDisplayMessage(getMessage(messageSource, "InputFormatException"));
        error.setErrorMessages(new ArrayList<String>() {{
            add(Constant.INPUT_FORMAT_EXCEPTION);
        }});
    }
}
