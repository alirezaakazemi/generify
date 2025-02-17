package com.generify.exception;

import com.generify.model.Constant;
import com.generify.model.ErrorCodes;
import org.springframework.context.MessageSource;

import java.util.ArrayList;

public class ServiceUnavailableException extends BaseException {

    public ServiceUnavailableException(MessageSource messageSource) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.SERVICE_UNAVAILABLE);
        error.setDisplayMessage(getMessage(messageSource, "ServiceUnavailable"));
        error.setErrorMessages(new ArrayList<String>() {{
            add(Constant.SERVICE_UNAVAILABLE);
        }});
    }
}
