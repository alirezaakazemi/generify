package com.ada.parsian.parsianmobilebank.exception;

import com.ada.parsian.parsianmobilebank.model.Constant;
import com.ada.parsian.parsianmobilebank.model.ErrorCodes;
import org.springframework.context.MessageSource;

import java.util.ArrayList;

public class InsufficientBalanceException extends BaseException {

    public InsufficientBalanceException(MessageSource messageSource) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.INSUFFICIENT_BALANCE);
        error.setDisplayMessage(getMessage(messageSource, "InsufficientBalance"));
        error.setErrorMessages(new ArrayList<String>() {{
            add(Constant.INSUFFICIENT_BALANCE);
        }});
    }
}
