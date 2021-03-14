package com.generify.thirdparty;

import com.generify.exception.*;
import com.generify.model.*;
import com.generify.model.card.IClientCardRequest;
import com.generify.repository.TransactionRepository;
import com.generify.repository.entity.Transaction;
import com.generify.repository.entity.TransactionLog;
import com.generify.repository.log.LogRepository;
import com.generify.thirdparty.charge.model.IThirdPartyError;
import com.google.gson.Gson;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Locale;

public abstract class AbstractThirdPartyCommand<T extends IClientCardRequest, K extends IThirdPartyRequest, L extends IThirdPartyResponse, M, P extends IThirdPartyError> {

    protected final MessageSource messageSource;
    protected Transaction transaction;
    private final LogRepository logRepository;
    private final TransactionRepository transactionRepository;
    protected final RequestHeaders headers;
    protected final M subTransaction;
    protected final JpaRepository<M, Long> subTransactionRepository;
    protected final byte transactionType;
    private final Class<P> thirdPartyErrorClass;

    public AbstractThirdPartyCommand(MessageSource messageSource, Transaction transaction, LogRepository logRepository,
                                     TransactionRepository transactionRepository, RequestHeaders headers, M subTransaction,
                                     JpaRepository<M, Long> subTransactionRepository, byte transactionType, Class<P> thirdPartyErrorClass) {
        this.messageSource = messageSource;
        this.transaction = transaction;
        this.logRepository = logRepository;
        this.transactionRepository = transactionRepository;
        this.headers = headers;
        this.subTransaction = subTransaction;
        this.subTransactionRepository = subTransactionRepository;
        this.transactionType = transactionType;
        this.thirdPartyErrorClass = thirdPartyErrorClass;
    }

    public abstract M execute(T clientRequest);

    protected abstract K createRequest(T clientRequest, M subTransaction);

    protected abstract Response<L> callService(K request) throws IOException;

    protected ServiceUnavailableException handleServiceUnAvailableException(IOException e, byte transactionType) {

        // Get exception
        ServiceUnavailableException serviceUnavailableException = new ServiceUnavailableException(messageSource);

        // Store received response body in log db
        storeServerLog(e.toString(), headers, LogType.THIRD_PARTY_SERVER_RESPONSE, transactionType);

        // Store clientResponse body in log db
        storeClientResponseLog(new Gson().toJson(serviceUnavailableException.getError()));

        // Return exception
        return serviceUnavailableException;
    }

    protected L handleResponse(Response<L> response) {

        if (response != null && response.body() != null) { // Successful response

            if (response.isSuccessful()) { // Successful response

                // Store received response body in log db
                storeServerLog(new Gson().toJson(response.body()), headers, LogType.THIRD_PARTY_SERVER_RESPONSE, transactionType);

                return response.body();

            } else { // Unsuccessful response

                // Get error
                HandledReceivedError<BaseException> handledReceivedError = handleError(response);

                // Store received response body in log db
                storeServerLog(handledReceivedError.getReceivedResponse(), headers, LogType.THIRD_PARTY_SERVER_RESPONSE, transactionType);

                // Store clientResponse body in log db
                storeClientResponseLog(new Gson().toJson(handledReceivedError.getException().getError()));

                // Throw exception
                throw handledReceivedError.getException();

            }

        } else { // UnSuccessful response, getError and return response

            // Get error
            HandledReceivedError<BaseException> handledReceivedError = handleError(response);

            // Store received response body in log db
            storeServerLog(handledReceivedError.getReceivedResponse(), headers, LogType.THIRD_PARTY_SERVER_RESPONSE, transactionType);

            // Store clientResponse body in log db
            storeClientResponseLog(new Gson().toJson(handledReceivedError.getException().getError()));

            // Throw exception
            throw handledReceivedError.getException();
        }
    }

    protected abstract M handleSuccessfulResponse(L response, RequestHeaders headers);

    private HandledReceivedError<BaseException> handleError(Response<L> response) {

        if (response == null || response.errorBody() == null) { // Null response received from thirdParty server

            // Update subTransaction
            updateSubTransactionInDB(null, TransactionStatus.ERROR.getValue());

            // Update transaction
            transaction = updateTransactionInDB(response != null ? String.valueOf(response.code()) : null, TransactionStatus.ERROR.getValue(),
                    Constant.NULL_RESPONSE, messageSource.getMessage("TransactionError", null, new Locale("fa", "IR")),
                    null);

            // Handle error and Return exception
            return new HandledReceivedError<>(new NullResponseException(messageSource), null);
        }

        try { // Get error from response and throw appropriate exception

            // Get errorBody
            String errorBody = response.errorBody().string();

            // Handle error and Return exception
            return new HandledReceivedError<>(getError(errorBody, response.code(), thirdPartyErrorClass), errorBody);

        } catch (IOException e) { // ErrorBody of response can't convert to string, So throw error exception

            // Update transaction
            transaction = updateTransactionInDB(String.valueOf(response.code()), TransactionStatus.ERROR.getValue(),
                    Constant.CANT_EXTRACT_STRING_VALUE_OF_ERROR_BODY, messageSource.getMessage("TransactionError", null, new Locale("fa", "IR")),
                    null);

            // Handle error and Return exception
            return new HandledReceivedError<>(new NullResponseException(messageSource), null);
        }
    }

    protected BaseException getError(String errorBody, int errorCode, Class<P> thirdPartyErrorClass) {

        if (isThirdPartyError(errorBody)) { // ErrorType is of thirdPartyErrorClass

            // get thirdPartyError from errorBody
            P thirdPartyError = new Gson().fromJson(errorBody, thirdPartyErrorClass);

            // Get clientError
            ClientError clientError = getClientError(thirdPartyError);

            // Update charge reserveStatus to FAIL
            updateSubTransactionInDB(null, TransactionStatus.FAIL.getValue());

            // Update transaction
            transaction = updateTransactionInDB(String.valueOf(errorCode), TransactionStatus.FAIL.getValue(), clientError.getErrorMessages().get(0),
                    clientError.getDisplayMessage(), thirdPartyError.getOriginalDisplayMessage());

            return new BaseException(clientError);

        } else {

            // Update charge reserveStatus to ERROR
            updateSubTransactionInDB(null, TransactionStatus.ERROR.getValue());

            // Update transaction
            transaction = updateTransactionInDB(String.valueOf(errorCode), TransactionStatus.ERROR.getValue(), Constant.ERROR_TYPE_IS_NOT_OF_BANK_ERROR,
                    messageSource.getMessage("TransactionError", null, new Locale("fa", "IR")), null);

            // Throw exception
            return new TransactionErrorException(messageSource);
        }
    }

    protected abstract boolean isThirdPartyError(String errorBody);

    protected abstract ClientError getClientError(P thirdPartyError);

    private Transaction updateTransactionInDB(String responseCode, Byte status, String failReason, String displayMessage, String originalDisplayMessage) {

        // Get current time
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // Initial transaction
        transaction.setResponseCode(responseCode);
        transaction.setStatus(status);
        transaction.setModifiedDate(now);
        transaction.setFailReason(failReason);
        transaction.setDisplayMessage(displayMessage);
        transaction.setOriginalDisplayMessage(originalDisplayMessage);

        // Store transaction
        return transactionRepository.save(transaction);
    }

    private TransactionLog getTransactionLog(String body, LogType logType, String clientIp, String mobileNumber, Byte transactionType) {

        // Initial transactionLog
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setClientIp(clientIp);
        transactionLog.setBody(body);
        transactionLog.setMobileNumber(mobileNumber);
        transactionLog.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        transactionLog.setTransactionType(transactionType);
        transactionLog.setType(logType.getValue());
        transactionLog.setTransactionId(transaction.getId());

        return transactionLog;
    }

    protected void storeClientResponseLog(String clientResponseBody) {

        // Get transactionLog
        TransactionLog transactionLog = getTransactionLog(clientResponseBody, LogType.CLIENT_RESPONSE, headers.getClientIp(), transaction.getMobileNumber(),
                (byte) transaction.getTransactionType());

        // Store log
        logRepository.save(transactionLog);
    }

    protected void storeServerLog(String body, RequestHeaders headers, LogType logType, byte transactionType) {

        // Get transactionLog
        TransactionLog transactionLog = getTransactionLog(body, logType, headers.getClientIp(), headers.getMobileNumber(), transactionType);

        // Store log
        logRepository.save(transactionLog);
    }

    protected abstract M updateSubTransactionInDB(L response, byte transactionStatus);

}
