package com.generify.exception;

import com.generify.model.Constant;
import com.generify.model.ErrorCodes;
import org.springframework.context.MessageSource;

import java.util.ArrayList;

public class TransactionNotSupportedException extends BaseException {

    public TransactionNotSupportedException(MessageSource messageSource) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.TRANSACTION_NOT_SUPPORTED);
        error.setDisplayMessage(getMessage(messageSource, "TransactionNotSupported"));
        error.setErrorMessages(new ArrayList<String>() {{
            add(Constant.TRANSACTION_NOT_SUPPORTED);
        }});
    }
}
