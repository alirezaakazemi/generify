package com.ada.parsian.parsianmobilebank.exception;

import com.ada.parsian.parsianmobilebank.model.Constant;
import com.ada.parsian.parsianmobilebank.model.ErrorCodes;
import org.springframework.context.MessageSource;

import java.util.ArrayList;

public class DailyMonthlyAmountExceedException extends BaseException {

    public DailyMonthlyAmountExceedException(MessageSource messageSource) {

        // Set ClientError fields
        error.setErrorCode(ErrorCodes.DAILY_MONTHLY_AMOUNT_EXCEED);
        error.setDisplayMessage(getMessage(messageSource, "DailyMonthlyAmountExceed"));
        error.setErrorMessages(new ArrayList<String>() {{
            add(Constant.DAILY_MONTHLY_AMOUNT_EXCEED);
        }});
    }
}
