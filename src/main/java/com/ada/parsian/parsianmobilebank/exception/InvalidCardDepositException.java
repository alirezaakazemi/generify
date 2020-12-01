package com.ada.parsian.parsianmobilebank.exception;

import com.ada.parsian.parsianmobilebank.model.Constant;
import com.ada.parsian.parsianmobilebank.model.ErrorCodes;
import org.springframework.context.MessageSource;

import java.util.ArrayList;

public class InvalidCardDepositException extends BaseException {

    public InvalidCardDepositException(MessageSource messageSource) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.INVALID_CARD_DEPOSIT);
        error.setDisplayMessage(getMessage(messageSource, "InvalidCardDeposit"));
        error.setErrorMessages(new ArrayList<String>() {{
            add(Constant.INVALID_CARD_DEPOSIT);
        }});
    }
}
