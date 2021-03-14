package com.generify.thirdparty.charge.command;

import com.generify.exception.ClientError;
import com.generify.exception.ExceptionUtil;
import com.generify.model.LogType;
import com.generify.model.RequestHeaders;
import com.generify.model.TransactionStatus;
import com.generify.model.TransactionType;
import com.generify.model.card.ClientCardChargeRequest;
import com.generify.repository.TransactionRepository;
import com.generify.repository.entity.Charge;
import com.generify.repository.entity.Transaction;
import com.generify.repository.log.LogRepository;
import com.generify.thirdparty.AbstractThirdPartyCommand;
import com.generify.thirdparty.charge.client.ChargeClient;
import com.generify.thirdparty.charge.client.model.ChargeRequest;
import com.generify.thirdparty.charge.client.model.ChargeResponse;
import com.generify.thirdparty.charge.model.ChargeError;
import com.generify.thirdparty.charge.model.DoChargeStatus;
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
