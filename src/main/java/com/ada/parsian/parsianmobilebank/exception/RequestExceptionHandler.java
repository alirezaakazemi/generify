package com.ada.parsian.parsianmobilebank.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    MessageSource messageSource;

    @Autowired
    public RequestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BaseException.class)
    public final ResponseEntity<ClientError> handleInvalidCardInfoException(BaseException ex, WebRequest request) {

        return new ResponseEntity<>(ex.error, HttpStatus.NOT_FOUND);
    }
}
