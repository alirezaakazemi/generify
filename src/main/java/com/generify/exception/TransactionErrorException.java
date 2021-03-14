package com.generify.exception;

import com.generify.model.Constant;
import com.generify.model.ErrorCodes;
import org.springframework.context.MessageSource;

import java.util.ArrayList;

public class TransactionErrorException extends BaseException {

    public TransactionErrorException(MessageSource messageSource) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.TRANSACTION_ERROR);
        error.setDisplayMessage(getMessage(messageSource, "TransactionError"));
        error.setErrorMessages(new ArrayList<String>() {{
            add(Constant.TRANSACTION_ERROR);
        }});
    }
}
