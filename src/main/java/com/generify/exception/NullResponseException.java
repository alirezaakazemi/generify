package com.generify.exception;

import com.generify.model.Constant;
import com.generify.model.ErrorCodes;
import org.springframework.context.MessageSource;

import java.util.ArrayList;

public class NullResponseException extends BaseException {

    public NullResponseException(MessageSource messageSource) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.NULL_RESPONSE);
        error.setDisplayMessage(getMessage(messageSource, "TransactionError"));
        error.setErrorMessages(new ArrayList<String>() {{
            add(Constant.NULL_RESPONSE);
        }});
    }
}
