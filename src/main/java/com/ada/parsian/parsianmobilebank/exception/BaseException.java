package com.ada.parsian.parsianmobilebank.exception;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BaseException extends RuntimeException {

    protected ClientError error;

    protected BaseException() {
        error = new ClientError();
    }

    public BaseException(ClientError error) {
        this.error = error;
    }

    public static String getMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, new Locale("fa", "IR"));
    }

    public ClientError getError() {
        return error;
    }
}
