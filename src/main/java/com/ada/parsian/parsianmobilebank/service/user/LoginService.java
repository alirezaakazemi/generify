package com.ada.parsian.parsianmobilebank.service.user;

import com.ada.parsian.parsianmobilebank.AppConfig;
import com.ada.parsian.parsianmobilebank.client.LoginClient;
import com.ada.parsian.parsianmobilebank.client.model.LoginRequest;
import com.ada.parsian.parsianmobilebank.client.model.LoginResponse;
import com.ada.parsian.parsianmobilebank.exception.ServiceUnavailableException;
import com.ada.parsian.parsianmobilebank.model.ClientLoginRequest;
import com.ada.parsian.parsianmobilebank.model.ClientLoginResponse;
import com.ada.parsian.parsianmobilebank.model.RequestHeaders;
import com.ada.parsian.parsianmobilebank.model.TransactionType;
import com.ada.parsian.parsianmobilebank.repository.TransactionRepository;
import com.ada.parsian.parsianmobilebank.repository.log.LogRepository;
import com.ada.parsian.parsianmobilebank.service.AbstractService;
import com.google.gson.Gson;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;

@Service
public class LoginService extends AbstractService<ClientLoginRequest, ClientLoginResponse, LoginRequest, LoginResponse> {

    /**
     * In this constructor you must pass messageSource param. in the subclass you should define parameter
     * as follow:
     * <p>-- @Qualifier(value = "messageSource") MessageSource messageSource
     * <p></p> The "messageSource" is defined in {@link AppConfig#messageSource()}
     *
     * @param messageSource used for reading persian messages from messages.properties file.
     */
    public LoginService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository, AppConfig appConfig) {
        super(messageSource, logRepository, transactionRepository, appConfig);
    }

    @Override
    public ClientLoginResponse execute(RequestHeaders headers, ClientLoginRequest clientRequest) {

        try {

            // Store transaction with initial data
            transaction = storeTransactionInDB(headers, null, null, null);

            // Store clientRequest in db
            storeClientRequestLog(clientRequest, headers);

            // Initial bankLoginRequest
            LoginRequest bankLoginRequest = createBankRequest(clientRequest, headers);

            // Store bankRequest body in log db
            storeRequestSentToBankLog(bankLoginRequest, headers);

            // Call login service
            Response<LoginResponse> response = callService(bankLoginRequest, headers);

            // Handle login response
            return createClientResponse(handleBankResponseAndLogReceivedResponse(response, headers));

        } catch (IOException e) { // Service is unavailable

            // Get exception
            ServiceUnavailableException serviceUnavailableException = new ServiceUnavailableException(messageSource);

            // Store clientResponse body in log db
            storeClientResponseLog(new Gson().toJson(serviceUnavailableException.getError()), headers);

            // Throw exception
            throw serviceUnavailableException;
        }
    }

    @Override
    protected void storeSubTransactionInDB(ClientLoginRequest clientRequest) {

    }

    @Override
    protected LoginRequest createBankRequest(ClientLoginRequest clientRequest, RequestHeaders headers) {

        // Initial loginRequest
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setChannelServiceType("MOBILE_BANK");
        loginRequest.setLocalPassword(clientRequest.getLocalPassword());

        LoginRequest.RequestBean requestBean = new LoginRequest.RequestBean(clientRequest.getUsername(), clientRequest.getPassword());
        loginRequest.setRequestBean(requestBean);

        // Return loginRequest
        return loginRequest;
    }

    @Override
    protected Map<String, String> getBankRequestHeader(RequestHeaders headers) {
        return null;
    }

    @Override
    protected Response<LoginResponse> callService(LoginRequest request, RequestHeaders requestHeaders) throws IOException {
        return LoginClient.instance.login(request).execute();
    }

    @Override
    protected void updateSubTransaction(LoginResponse response, Byte transactionStatus) {

    }

    @Override
    protected ClientLoginResponse createClientResponse(LoginResponse response) {

        // Initial clientLoginResponse
        ClientLoginResponse clientLoginResponse = new ClientLoginResponse();
        clientLoginResponse.setLoginToken(response.getLoginToken());
        clientLoginResponse.setCustomerNumber(response.getCustomerNumber());
        clientLoginResponse.setSessionId(response.getSessionId());
        clientLoginResponse.setCode(response.getCode());
        clientLoginResponse.setForeignName(response.getForeignName());
        clientLoginResponse.setGender(response.getGender());
        clientLoginResponse.setLastLoginTime(response.getLastLoginTime());
        clientLoginResponse.setName(response.getName());
        clientLoginResponse.setTitle(response.getTitle());
        clientLoginResponse.setRequiredChangeSecondPassword(response.isRequiredChangeSecondPassword());

        return clientLoginResponse;
    }

    @Override
    protected byte getTransactionType() {
        return TransactionType.LOGIN.getValue();
    }
}
