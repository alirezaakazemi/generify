package com.generify.service.user;

import com.generify.AppConfig;
import com.generify.client.LoginClient;
import com.generify.client.model.LoginRequest;
import com.generify.client.model.LoginResponse;
import com.generify.exception.ServiceUnavailableException;
import com.generify.model.ClientLoginRequest;
import com.generify.model.ClientLoginResponse;
import com.generify.model.RequestHeaders;
import com.generify.model.TransactionType;
import com.generify.repository.CustomerRepository;
import com.generify.repository.TransactionRepository;
import com.generify.repository.entity.Customer;
import com.generify.repository.log.LogRepository;
import com.generify.service.AbstractService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

@Service
public class LoginService extends AbstractService<ClientLoginRequest, ClientLoginResponse, LoginRequest, LoginResponse> {

    @Autowired
    CustomerRepository customerRepository;

    /**
     * In this constructor you must pass messageSource param. in the subclass you should define parameter
     * as follow:
     * <p>-- @Qualifier(value = "messageSource") MessageSource messageSource
     * <p></p> The "messageSource" is defined in {@link AppConfig#messageSource()}
     *
     * @param messageSource used for reading persian messages from messages.properties file.
     */
    public LoginService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository, AppConfig appConfig, CustomerRepository customerRepository) {
        super(messageSource, logRepository, transactionRepository, appConfig);
        this.customerRepository = customerRepository;
    }

    @Override
    public ClientLoginResponse execute(RequestHeaders headers, ClientLoginRequest clientRequest) {

        try {

            // Store transaction with initial data
            transaction = storeTransactionInDB(headers, null, null, null);

            // Store clientRequest in db
            storeClientRequestLog(clientRequest, headers);

            // Store cardTransfer in db
            storeSubTransactionInDB(clientRequest, headers);

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
    protected void storeSubTransactionInDB(ClientLoginRequest clientRequest, RequestHeaders headers) {
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
    protected LoginResponse handleBankResponseAndLogReceivedResponse(Response<LoginResponse> response, RequestHeaders headers) {

        // Handle response
        LoginResponse loginResponse = super.handleBankResponseAndLogReceivedResponse(response, headers);

        // Login is successful, So store customer in DB if he is not stored before
        // Get customer with mobileNumber
        Customer customer = customerRepository.findByMobileNumber(headers.getMobileNumber());

        if (customer == null) { // Customer not stored before
            customer = new Customer();
            customer.setCif(loginResponse.getCustomerNumber());
            customer.setGender(loginResponse.getGender());
            customer.setMobileNumber(headers.getMobileNumber());
            customer.setName(loginResponse.getName());

            // Get current time
            Timestamp now = new Timestamp(System.currentTimeMillis());
            customer.setCreatedDate(now);
            customer.setModifiedDate(now);

            // Store customer
            customer = customerRepository.save(customer);
        }

        return loginResponse;
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
