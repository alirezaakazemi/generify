package com.ada.parsian.parsianmobilebank.exception;

import com.ada.parsian.parsianmobilebank.model.Constant;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.model.ChargeError;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ExceptionUtil {

    public static boolean isBankError(String error) {

        return new Gson().fromJson(error, BankError.class).getCode() != 0;
    }

    public static boolean isChargeError(String error) {
        return new Gson().fromJson(error, ChargeError.class).getErrorCode() != null;
    }

    public static ClientError getClientError(BankError bankError, String errorCode) {

        return getClientError(errorCode, bankError.getDescription().getMessage(), bankError.getDescription().getEnMessage());
    }

    public static ClientError getClientError(ChargeError chargeError, String errorCode) {

        return getClientError(errorCode, chargeError.getErrorMessage(), Constant.CHARGE_NOT_AVAILABLE);
    }

    public static ClientError getClientError(String errorCode, String errorMessage, String enErrorMessage) {

        // Set ClientError fields
        ClientError clientError = new ClientError();
        clientError.setErrorCode(errorCode);
        clientError.setDisplayMessage(errorMessage);
        clientError.setErrorMessages(new ArrayList<String>() {{
            add(enErrorMessage);
        }});
        return clientError;
    }
}
