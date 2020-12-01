package com.ada.parsian.parsianmobilebank.thirdparty.charge.command;

import com.ada.parsian.parsianmobilebank.exception.ClientError;
import com.ada.parsian.parsianmobilebank.exception.ExceptionUtil;
import com.ada.parsian.parsianmobilebank.model.LogType;
import com.ada.parsian.parsianmobilebank.model.RequestHeaders;
import com.ada.parsian.parsianmobilebank.model.TransactionStatus;
import com.ada.parsian.parsianmobilebank.model.TransactionType;
import com.ada.parsian.parsianmobilebank.model.card.ClientCardChargeRequest;
import com.ada.parsian.parsianmobilebank.repository.TransactionRepository;
import com.ada.parsian.parsianmobilebank.repository.entity.Charge;
import com.ada.parsian.parsianmobilebank.repository.entity.Transaction;
import com.ada.parsian.parsianmobilebank.repository.log.LogRepository;
import com.ada.parsian.parsianmobilebank.thirdparty.AbstractThirdPartyCommand;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.ChargeClient;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model.ChargeRequest;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model.ChargeResponse;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.model.ChargeError;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.model.DoChargeStatus;
import com.google.gson.Gson;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;
import retrofit2.Response;

import java.io.IOException;

public class ChargeCommand extends AbstractThirdPartyCommand<ClientCardChargeRequest, ChargeRequest, ChargeResponse, Charge, ChargeError> {


    public ChargeCommand(MessageSource messageSource, Transaction transaction, LogRepository logRepository, TransactionRepository transactionRepository,
                         RequestHeaders headers, Charge subTransaction, JpaRepository<Charge, Long> subTransactionRepository, byte transactionType) {
        super(messageSource, transaction, logRepository, transactionRepository, headers, subTransaction, subTransactionRepository, transactionType,
                ChargeError.class);
    }

    @Override
    public Charge execute(ClientCardChargeRequest clientRequest) {

        // Initial chargeRequest
        ChargeRequest chargeRequest = createRequest(clientRequest, subTransaction);

        try {

            // Store body of chargeRequest in log db
            storeServerLog(new Gson().toJson(chargeRequest), headers, LogType.THIRD_PARTY_SERVER_REQUEST, TransactionType.CHARGE.getValue());

            // Call service
            Response<ChargeResponse> response = callService(chargeRequest);

            // Handle response
            ChargeResponse chargeResponse = handleResponse(response);

            // Handle successful response
            return handleSuccessfulResponse(chargeResponse, headers);

        } catch (IOException e) {

            // Handle and throw exception
            throw handleServiceUnAvailableException(e, TransactionType.CHARGE.getValue());
        }
    }

    @Override
    protected ChargeRequest createRequest(ClientCardChargeRequest clientRequest, Charge subTransaction) {

        ChargeRequest chargeRequest = new ChargeRequest();
        chargeRequest.setAmount(transaction.getAmount());
        chargeRequest.setMobileNumber(clientRequest.getDestPhoneNumber());
        chargeRequest.setProductId(Long.parseLong(clientRequest.getProductId()));
        chargeRequest.setRequestId(subTransaction.getRequestId());
        chargeRequest.setServiceType(Integer.parseInt(clientRequest.getServiceType()));
        chargeRequest.setTransaction_code(transaction.getId());

        return chargeRequest;
    }

    @Override
    protected Response<ChargeResponse> callService(ChargeRequest request) throws IOException {
        return ChargeClient.instance.charge(request).execute();
    }

    @Override
    protected Charge handleSuccessfulResponse(ChargeResponse response, RequestHeaders headers) {

        if (response.getStatus() == DoChargeStatus.REQUEST_REGISTERED.getValue()) { // Charge done

            // Update charge reserveStatus to SUCCESS
            return updateSubTransactionInDB(response, TransactionStatus.SUCCESS.getValue());

        } else if (response.getStatus() == DoChargeStatus.FAILED.getValue()) {

            // Update charge reserveStatus to SUCCESS
            return updateSubTransactionInDB(response, TransactionStatus.FAIL.getValue());
        }

        // Update charge reserveStatus to SUCCESS
        return updateSubTransactionInDB(response, TransactionStatus.ERROR.getValue());
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
    protected Charge updateSubTransactionInDB(ChargeResponse response, byte transactionStatus) {

        subTransaction.setChargeStatus(transactionStatus);

        // Update charge
        return subTransactionRepository.save(subTransaction);
    }
}
