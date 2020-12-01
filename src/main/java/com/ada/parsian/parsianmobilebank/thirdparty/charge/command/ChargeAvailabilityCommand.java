package com.ada.parsian.parsianmobilebank.thirdparty.charge.command;

import com.ada.parsian.parsianmobilebank.exception.BaseException;
import com.ada.parsian.parsianmobilebank.exception.ClientError;
import com.ada.parsian.parsianmobilebank.exception.ExceptionUtil;
import com.ada.parsian.parsianmobilebank.model.*;
import com.ada.parsian.parsianmobilebank.model.card.ClientCardChargeRequest;
import com.ada.parsian.parsianmobilebank.repository.TransactionRepository;
import com.ada.parsian.parsianmobilebank.repository.entity.Charge;
import com.ada.parsian.parsianmobilebank.repository.entity.Transaction;
import com.ada.parsian.parsianmobilebank.repository.log.LogRepository;
import com.ada.parsian.parsianmobilebank.thirdparty.AbstractThirdPartyCommand;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.ChargeClient;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model.ChargeAvailabilityRequest;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model.ChargeAvailabilityResponse;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.model.ChargeError;
import com.google.gson.Gson;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;
import retrofit2.Response;

import java.io.IOException;

public class ChargeAvailabilityCommand extends AbstractThirdPartyCommand<ClientCardChargeRequest, ChargeAvailabilityRequest,
        ChargeAvailabilityResponse, Charge, ChargeError> {


    public ChargeAvailabilityCommand(MessageSource messageSource, Transaction transaction, LogRepository logRepository, TransactionRepository transactionRepository, RequestHeaders headers, Charge subTransaction,
                                     JpaRepository<Charge, Long> subTransactionRepository, byte transactionType) {
        super(messageSource, transaction, logRepository, transactionRepository, headers, subTransaction, subTransactionRepository,
                transactionType, ChargeError.class);
    }

    @Override
    public Charge execute(ClientCardChargeRequest clientRequest) {

        // Initial chargeAvailabilityRequest
        ChargeAvailabilityRequest chargeAvailabilityRequest = createRequest(clientRequest, subTransaction);

        try {

            // Store body of checkChargeAvailability in log db
            storeServerLog(new Gson().toJson(chargeAvailabilityRequest), headers, LogType.THIRD_PARTY_SERVER_REQUEST, TransactionType.CHARGE.getValue());

            // Call service
            Response<ChargeAvailabilityResponse> response = callService(chargeAvailabilityRequest);

            // Handle response
            ChargeAvailabilityResponse chargeAvailabilityResponse = handleResponse(response);

            // Handle successful response
            return handleSuccessfulResponse(chargeAvailabilityResponse, headers);

        } catch (IOException e) {

            // Handle and throw exception
            throw handleServiceUnAvailableException(e, TransactionType.CHARGE.getValue());
        }
    }


    @Override
    protected ChargeAvailabilityRequest createRequest(ClientCardChargeRequest clientRequest, Charge subTransaction) {

        // Initial chargeAvailabilityRequest
        ChargeAvailabilityRequest chargeAvailabilityRequest = new ChargeAvailabilityRequest();
        chargeAvailabilityRequest.setServiceType(Integer.parseInt(clientRequest.getServiceType()));
        chargeAvailabilityRequest.setProductId(Long.parseLong(clientRequest.getProductId()));
        chargeAvailabilityRequest.setAmount(clientRequest.getAmount());
        chargeAvailabilityRequest.setDestPhoneNumber(clientRequest.getDestPhoneNumber());

        return chargeAvailabilityRequest;
    }

    @Override
    protected Response<ChargeAvailabilityResponse> callService(ChargeAvailabilityRequest request) throws IOException {
        return ChargeClient.instance.chargeAvailability(request).execute();
    }

    @Override
    protected Charge handleSuccessfulResponse(ChargeAvailabilityResponse response, RequestHeaders headers) {

        if (response.getStatus() == ChargeReserveStatus.SUCCESS.getValue()) { // Charge reserve is Success

            // Update charge reserveStatus to SUCCESS
            return updateSubTransactionInDB(response, ChargeReserveStatus.SUCCESS.getValue());

        } else { // Charge reserve is Failed

            // Update charge reserveStatus to FAIL
            updateSubTransactionInDB(null, ChargeReserveStatus.FAIL.getValue());

            // Store received response body in log db
            storeServerLog(new Gson().toJson(response), headers, LogType.THIRD_PARTY_SERVER_RESPONSE, TransactionType.CHARGE.getValue());

            // Throw charge not available exception
            throw new BaseException(ExceptionUtil.getClientError("", response.getErrorMessage(), Constant.CHARGE_NOT_AVAILABLE));
        }
    }

    @Override
    protected boolean isThirdPartyError(String errorBody) {
        return ExceptionUtil.isChargeError(errorBody);
    }

    @Override
    protected ClientError getClientError(ChargeError thirdPartyError) {
        return ExceptionUtil.getClientError(thirdPartyError, "");
    }

    @Override
    protected Charge updateSubTransactionInDB(ChargeAvailabilityResponse response, byte transactionStatus) {

        subTransaction.setReserveStatus((long) transactionStatus);

        if (transactionStatus == TransactionStatus.SUCCESS.getValue()) {

            subTransaction.setRequestId(response.getRequestId());
        }

        // Update charge
        return subTransactionRepository.save(subTransaction);
    }
}