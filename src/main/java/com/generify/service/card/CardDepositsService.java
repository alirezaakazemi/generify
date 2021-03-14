package com.generify.service.card;

import com.generify.AppConfig;
import com.generify.client.card.CardClient;
import com.generify.client.card.model.BankCardDepositsRequest;
import com.generify.client.card.model.BankCardDepositsResponse;
import com.generify.model.RequestHeaders;
import com.generify.model.SourceType;
import com.generify.model.TransactionType;
import com.generify.model.card.ClientCardDepositsRequest;
import com.generify.model.card.ClientCardDepositsResponse;
import com.generify.repository.TransactionRepository;
import com.generify.repository.log.LogRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

@Service
public class CardDepositsService extends AbstractCardService<ClientCardDepositsRequest, ClientCardDepositsResponse, BankCardDepositsRequest, BankCardDepositsResponse> {

    /**
     * In this constructor you must pass messageSource param. in the subclass you should define parameter
     * as follow:
     * <p>-- @Qualifier(value = "messageSource") MessageSource messageSource
     * <p></p> The "messageSource" is defined in {@link AppConfig#messageSource()}
     *
     * @param messageSource         used for reading persian messages from messages.properties file.
     * @param logRepository
     * @param transactionRepository
     */
    public CardDepositsService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository, AppConfig appConfig) {
        super(messageSource, logRepository, transactionRepository, appConfig);
    }

    @Override
    public ClientCardDepositsResponse execute(RequestHeaders headers, ClientCardDepositsRequest clientRequest) {
        try {

            // Store transaction with initial data
            transaction = storeTransactionInDB(headers, clientRequest.getPan(), SourceType.CARD.getValue(), null);

            // Store clientRequest in db
            storeClientRequestLog(clientRequest, headers);

            // Initial bankOwnerRequest
            BankCardDepositsRequest bankCardDepositsRequest = createBankRequest(clientRequest, headers);

            // Store bankRequest body in log db
            storeRequestSentToBankLog(bankCardDepositsRequest, headers);

            // Call owner service
            Response<BankCardDepositsResponse> response = callService(bankCardDepositsRequest, headers);

            // Handle cardDeposits response
            return createClientResponse(handleBankResponseAndLogReceivedResponse(response, headers));


        } catch (IOException e) { // Service is unavailable

            // Handle and throw exception
            throw handleServiceUnAvailableException(headers, e);
        }

    }

    @Override
    protected void storeSubTransactionInDB(ClientCardDepositsRequest clientRequest, RequestHeaders headers) {

    }

    @Override
    protected BankCardDepositsRequest createBankRequest(ClientCardDepositsRequest clientRequest, RequestHeaders headers) {
        BankCardDepositsRequest bankCardDepositsRequest = new BankCardDepositsRequest();
        bankCardDepositsRequest.setCif(clientRequest.getCif());
        bankCardDepositsRequest.setPan(clientRequest.getPan());
        return bankCardDepositsRequest;
    }

    @Override
    protected Response<BankCardDepositsResponse> callService(BankCardDepositsRequest request, RequestHeaders requestHeaders) throws IOException {
        return CardClient.instance.deposits(getBankRequestHeader(requestHeaders), request).execute();
    }

    @Override
    protected void updateSubTransaction(BankCardDepositsResponse response, Byte transactionStatus) {

    }

    @Override
    protected ClientCardDepositsResponse createClientResponse(BankCardDepositsResponse response) {
        ClientCardDepositsResponse clientCardDepositsResponse = new ClientCardDepositsResponse();
        clientCardDepositsResponse.setTotalRecord(response.getTotalRecord());
        clientCardDepositsResponse.setBankDeposit(response.getDeposits());

        return clientCardDepositsResponse;
    }

    @Override
    protected byte getTransactionType() {
        return TransactionType.CARD_DEPOSITS.getValue();
    }
}
