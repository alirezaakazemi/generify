package com.ada.parsian.parsianmobilebank.service;

import com.ada.parsian.parsianmobilebank.AppConfig;
import com.ada.parsian.parsianmobilebank.client.model.IBankRequest;
import com.ada.parsian.parsianmobilebank.client.model.IBankResponse;
import com.ada.parsian.parsianmobilebank.exception.*;
import com.ada.parsian.parsianmobilebank.model.*;
import com.ada.parsian.parsianmobilebank.repository.TransactionRepository;
import com.ada.parsian.parsianmobilebank.repository.entity.Transaction;
import com.ada.parsian.parsianmobilebank.repository.entity.TransactionLog;
import com.ada.parsian.parsianmobilebank.repository.log.LogRepository;
import com.google.gson.Gson;
import org.springframework.context.MessageSource;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Abstract class for call and handle bank services. Common methods like a method for store request logs to db are implemented in this abstract class
 * and other specific methods like method for create client response are abstract and must be implemented in each sub service.
 *
 * <p>The workflow should be as follows:
 * <p>1- Create bankRequest with {@link AbstractService#createBankRequest(IClientRequest, RequestHeaders)}
 * <p>2- Call service of bank with {@link AbstractService#callService(IBankRequest, RequestHeaders)}
 * <p>3- Handle response of {@link AbstractService#callService(IBankRequest, RequestHeaders)} with {@link AbstractService#handleBankResponseAndLogReceivedResponse(Response, RequestHeaders)}
 * <p>4- Write necessary data to database with {@link AbstractService#storeTransactionInDB(RequestHeaders, String, Byte, Long)}
 * <p>5- Create client response with {@link AbstractService#createClientResponse(IBankResponse)}
 * <p>6- Write client request/response and bank request/response to database with {@link AbstractService#storeClientRequestLog(IClientRequest, RequestHeaders)}
 * And {@link AbstractService#storeClientResponseLog(String, RequestHeaders)} (IClientResponse, RequestHeaders)} And {@link AbstractService#storeRequestSentToBankLog(IBankRequest, RequestHeaders)}
 * And {@link AbstractService#storeResponseReceivedFromBankLog(String, RequestHeaders)} (IBankResponse, RequestHeaders)}
 * <p>If an error occurred in {@link AbstractService#callService(IBankRequest, RequestHeaders)} then in method {@link AbstractService#handleError(Response, RequestHeaders)}
 * handle the error and throw exception
 *
 * @param <T> Body of client request and is of type {@link IClientRequest}
 * @param <K> Body of response sent to client and is of type {@link IClientResponse}
 * @param <L> Body of request sent to bank and is of type {@link IBankRequest}
 * @param <M> Body of response received from bank and is of type {@link IBankResponse}
 */

public abstract class AbstractService<T extends IClientRequest, K extends IClientResponse, L extends IBankRequest, M extends IBankResponse> {

    protected MessageSource messageSource;
    protected Transaction transaction;
    protected LogRepository logRepository;
    protected TransactionRepository transactionRepository;
    protected AppConfig appConfig;

    /**
     * In this constructor you must pass messageSource param. in the subclass you should define parameter
     * as follow:
     * <p>-- @Qualifier(value = "messageSource") MessageSource messageSource
     * <p></p> The "messageSource" is defined in {@link AppConfig#messageSource()}
     *
     * @param messageSource         used for reading persian messages from messages.properties file.
     * @param transactionRepository
     */
    public AbstractService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository, AppConfig appConfig) {
        this.messageSource = messageSource;
        this.logRepository = logRepository;
        this.transactionRepository = transactionRepository;
        this.appConfig = appConfig;
    }

    /**
     * In this method you must perform the workflow explain in class help.
     *
     * @param headers       Headers sent from client
     * @param clientRequest request of client
     * @return Return correspond response based on client request
     */
    public abstract K execute(RequestHeaders headers, T clientRequest);

    /**
     * Store the data needed to database in this method
     *
     * @param headers    Headers sent from client
     * @param source
     * @param sourceType
     * @param amount
     * @return
     */
    protected Transaction storeTransactionInDB(RequestHeaders headers, String source, Byte sourceType, Long amount) {

        // Get current time
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // Initial transaction
        Transaction transaction = new Transaction();
        transaction.setClientIp(headers.getClientIp());
        transaction.setCreatedDate(now);
        transaction.setModifiedDate(now);
        transaction.setMobileNumber(headers.getMobileNumber());
        transaction.setTransactionType(getTransactionType());
        transaction.setCif(headers.getCif());
        transaction.setAmount(amount);
        transaction.setSource(source);
        transaction.setSourceType(sourceType);

        // Store transaction
        return transactionRepository.save(transaction);
    }

    protected abstract void storeSubTransactionInDB(T clientRequest, RequestHeaders headers);

    /**
     * Store body of client request in database
     *
     * @param clientRequest
     * @param headers       Headers sent from client
     */
    protected void storeClientRequestLog(T clientRequest, RequestHeaders headers) {

        // Get transactionLog
        TransactionLog transactionLog = getTransactionLog(new Gson().toJson(clientRequest), LogType.CLIENT_REQUEST, headers);

        // Store log
        logRepository.save(transactionLog);
    }

    /**
     * Create the request should sent to bank
     *
     * @param clientRequest
     * @param headers       Headers sent from client
     * @return
     */
    protected abstract L createBankRequest(T clientRequest, RequestHeaders headers);

    /**
     * Store body of request sent to bank in database
     *
     * @param request
     * @param headers Headers sent from client
     */
    protected void storeRequestSentToBankLog(L request, RequestHeaders headers) {

        // Get transactionLog
        TransactionLog transactionLog = getTransactionLog(new Gson().toJson(request), LogType.BANK_REQUEST, headers);

        // Store log
        logRepository.save(transactionLog);
    }

    protected Map<String, String> getBankRequestHeader(RequestHeaders headers) {
        Map<String, String> bankHeaders = new HashMap<>();
        bankHeaders.put(Constant.TOKEN_HEADER, headers.getSessionId());

        return bankHeaders;
    }

    /**
     * Call service of bank with this method
     *
     * @param request
     * @param requestHeaders
     * @return
     * @throws IOException
     */
    protected abstract Response<M> callService(L request, RequestHeaders requestHeaders) throws IOException;

    /**
     * When bank server is unAvailable, handle and throw exception with this method
     *
     * @param headers Headers sent from client
     * @param e       exception thrown by bank server
     * @return {@link ServiceUnavailableException}
     */
    protected ServiceUnavailableException handleServiceUnAvailableException(RequestHeaders headers, IOException e) {

        // Get exception
        ServiceUnavailableException serviceUnavailableException = new ServiceUnavailableException(messageSource);

        // Store received response body in log db
        storeResponseReceivedFromBankLog(e.toString(), headers);

        // Store clientResponse body in log db
        storeClientResponseLog(new Gson().toJson(serviceUnavailableException.getError()), headers);

        // Retrun exception
        return serviceUnavailableException;
    }

    /**
     * Handle the response received from {@link AbstractService#callService(IBankRequest, RequestHeaders)}
     *
     * @param response
     * @param headers  Headers sent from client
     * @return
     */
    protected M handleBankResponseAndLogReceivedResponse(Response<M> response, RequestHeaders headers) {

        if (response != null && response.body() != null) { // Successful response

            if (response.isSuccessful()) { // Successful response

                // Store received response body in log db
                storeResponseReceivedFromBankLog(new Gson().toJson(response.body()), headers);

                // Update transaction
                transaction = updateSuccessTransaction(new Timestamp(System.currentTimeMillis()), response.body());

                // Update sub transaction
                updateSubTransaction(response.body(), TransactionStatus.SUCCESS.getValue());

                // Store clientResponse body in log db
                storeClientResponseLog(new Gson().toJson(createClientResponse(response.body())), headers);

                // Return response
                return response.body();

            } else { // Unsuccessful response

                // Get error
                HandledReceivedError<BaseException> handledReceivedError = handleError(null, headers);

                // Store received response body in log db
                storeResponseReceivedFromBankLog(handledReceivedError.getReceivedResponse(), headers);

                // Store clientResponse body in log db
                storeClientResponseLog(new Gson().toJson(handledReceivedError.getException().getError()), headers);

                // Throw exception
                throw handledReceivedError.getException();
            }

        } else { // UnSuccessful response, getError and return response

            // Get error
            HandledReceivedError<BaseException> handledReceivedError = handleError(response, headers);

            // Store received response body in log db
            storeResponseReceivedFromBankLog(handledReceivedError.getReceivedResponse(), headers);

            // Store clientResponse body in log db
            storeClientResponseLog(new Gson().toJson(handledReceivedError.getException().getError()), headers);

            // Throw exception
            throw handledReceivedError.getException();
        }

    }

    protected Transaction updateSuccessTransaction(Timestamp transactionDate, M response) {

        // Update transaction
        return updateTransactionInDB("200", TransactionStatus.SUCCESS.getValue(), transactionDate,
                null, null, null, null);

    }

    protected abstract void updateSubTransaction(M response, Byte transactionStatus);

    protected Transaction updateTransactionInDB(String responseCode, Byte status, Timestamp transactionDate, String failReason,
                                                String displayMessage, String originalFailReason, String originalDisplayMessage) {

        // Get current time
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // Initial transaction
        transaction.setResponseCode(responseCode);
        transaction.setStatus(status);
        transaction.setTransactionDate(transactionDate);
        transaction.setModifiedDate(now);
        transaction.setFailReason(failReason);
        transaction.setDisplayMessage(displayMessage);
        transaction.setOriginalFailReason(originalFailReason);
        transaction.setOriginalDisplayMessage(originalDisplayMessage);

        // Store transaction
        return transactionRepository.save(transaction);
    }

    /**
     * Create appropriate response for client in this method
     *
     * @param response
     * @return
     */
    protected abstract K createClientResponse(M response);

    protected abstract byte getTransactionType();

    /**
     * If an error occurred in handle the error with this method.
     *
     * @param response
     * @param headers  Headers sent from client
     * @return
     */
    protected HandledReceivedError<BaseException> handleError(Response<M> response, RequestHeaders headers) {

        if (response == null || response.errorBody() == null) { // Null response received from bank

            // Update transaction
            transaction = updateTransactionInDB(response != null ? String.valueOf(response.code()) : null, TransactionStatus.ERROR.getValue(),
                    null, Constant.NULL_RESPONSE, messageSource.getMessage("TransactionError", null, new Locale("fa", "IR")),
                    null, null);

            // Update sub transaction
            updateSubTransaction(null, TransactionStatus.ERROR.getValue());

            // Handle error and Return exception
            return new HandledReceivedError<>(new NullResponseException(messageSource), null);
        }

        try { // Get error from response and throw appropriate exception

            // Get errorBody
            String errorBody = response.errorBody().string();

            // Handle error and Return exception
            return new HandledReceivedError<>(getError(errorBody, response.code()), errorBody);

        } catch (IOException e) { // ErrorBody of response can't convert to string, So throw error exception

            // Update transaction
            transaction = updateTransactionInDB(String.valueOf(response.code()), TransactionStatus.ERROR.getValue(), null,
                    Constant.CANT_EXTRACT_STRING_VALUE_OF_ERROR_BODY, messageSource.getMessage("TransactionError", null, new Locale("fa", "IR")),
                    null, null);

            // Update sub transaction
            updateSubTransaction(null, TransactionStatus.ERROR.getValue());

            // Handle error and Return exception
            return new HandledReceivedError<>(new NullResponseException(messageSource), null);
        }
    }

    protected BaseException getError(String errorBody, int errorCode) {

        if (!ExceptionUtil.isBankError(errorBody)) {  // Check error is of type BankError

            // Update transaction
            transaction = updateTransactionInDB(String.valueOf(errorCode), TransactionStatus.ERROR.getValue(), null, Constant.ERROR_TYPE_IS_NOT_OF_BANK_ERROR,
                    messageSource.getMessage("TransactionError", null, new Locale("fa", "IR")), null, null);

            // Update sub transaction
            updateSubTransaction(null, TransactionStatus.ERROR.getValue());

            // Throw exception
            return new TransactionErrorException(messageSource);
        }

        // get bankError from errorBody
        BankError bankError = new Gson().fromJson(errorBody, BankError.class);

        // Get clientError
        String serverErrorCode = "";
        ClientError clientError = ExceptionUtil.getClientError(bankError, serverErrorCode);

        // Update transaction
        transaction = updateTransactionInDB(String.valueOf(errorCode), TransactionStatus.FAIL.getValue(), null, clientError.getErrorMessages().get(0),
                clientError.getDisplayMessage(), bankError.getExceptionDetail(), bankError.getDescription().getMessage());

        // Update sub transaction
        updateSubTransaction(null, TransactionStatus.FAIL.getValue());

        return new BaseException(clientError);
    }

    /**
     * If an error occurred and you can't handle it, Create default error in this method
     *
     * @return
     */
    protected ClientError getDefaultError() {
        return new ClientError();
    }

    /**
     * Store body of response sent to client in database
     *
     * @param clientResponseBody
     * @param headers            Headers sent from client
     */
    protected void storeClientResponseLog(String clientResponseBody, RequestHeaders headers) {

        // Get transactionLog
        TransactionLog transactionLog = getTransactionLog(clientResponseBody, LogType.CLIENT_RESPONSE, headers);

        // Store log
        logRepository.save(transactionLog);
    }

    /**
     * Store body of response received from bank in database
     *
     * @param response
     * @param headers  Headers sent from client
     */
    protected void storeResponseReceivedFromBankLog(String response, RequestHeaders headers) {

        // Get transactionLog
        TransactionLog transactionLog = getTransactionLog(response, LogType.BANK_RESPONSE, headers);

        // Store log
        logRepository.save(transactionLog);
    }

    private TransactionLog getTransactionLog(String body, LogType logType, RequestHeaders requestHeaders) {

        // Initial transactionLog
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setClientIp(requestHeaders.getClientIp());
        transactionLog.setMobileNumber(requestHeaders.getMobileNumber());
        transactionLog.setCif(requestHeaders.getCif());
        transactionLog.setBody(body);
        transactionLog.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        transactionLog.setTransactionType(getTransactionType());
        transactionLog.setType(logType.getValue());
        transactionLog.setTransactionId(transaction.getId());
        transactionLog.setHeaders(requestHeaders.toString());

        return transactionLog;
    }
}
