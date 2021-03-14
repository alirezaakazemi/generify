package com.generify.exception;

import com.generify.model.Constant;
import com.generify.model.ErrorCodes;
import org.springframework.context.MessageSource;

import java.util.ArrayList;

public class InvalidCardInfoException extends BaseException {

    public InvalidCardInfoException(MessageSource messageSource) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.INVALID_CARD_INFO);
        error.setDisplayMessage(getMessage(messageSource, "InvalidCardInfo"));
        error.setErrorMessages(new ArrayList<String>() {{
            add(Constant.INVALID_CARD_INFO);
        }});
    }
}
