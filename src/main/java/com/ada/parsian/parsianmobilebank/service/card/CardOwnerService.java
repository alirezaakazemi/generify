package com.ada.parsian.parsianmobilebank.service.card;

import com.ada.parsian.parsianmobilebank.AppConfig;
import com.ada.parsian.parsianmobilebank.client.card.CardClient;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardOwnerRequest;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardOwnerResponse;
import com.ada.parsian.parsianmobilebank.model.RequestHeaders;
import com.ada.parsian.parsianmobilebank.model.SourceType;
import com.ada.parsian.parsianmobilebank.model.TransactionType;
import com.ada.parsian.parsianmobilebank.model.card.ClientOwnerRequest;
import com.ada.parsian.parsianmobilebank.model.card.ClientOwnerResponse;
import com.ada.parsian.parsianmobilebank.repository.TransactionRepository;
import com.ada.parsian.parsianmobilebank.repository.log.LogRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

@Service
public class CardOwnerService extends AbstractCardService<ClientOwnerRequest, ClientOwnerResponse, BankCardOwnerRequest, BankCardOwnerResponse> {


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
    public CardOwnerService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository, AppConfig appConfig) {
        super(messageSource, logRepository, transactionRepository, appConfig);
    }

    @Override
    public ClientOwnerResponse execute(RequestHeaders headers, ClientOwnerRequest clientRequest) {

        try {

            // Store transaction with initial data
            transaction = storeTransactionInDB(headers, clientRequest.getPan(), SourceType.CARD.getValue(), null);

            // Store clientRequest in db
            storeClientRequestLog(clientRequest, headers);

            // Initial bankOwnerRequest
            BankCardOwnerRequest bankCardOwnerRequest = createBankRequest(clientRequest, headers);

            // Store bankRequest body in log db
            storeRequestSentToBankLog(bankCardOwnerRequest, headers);

            // Call owner service
            Response<BankCardOwnerResponse> response = callService(bankCardOwnerRequest, headers);

            // Handle cardOwner response
            return createClientResponse(handleBankResponseAndLogReceivedResponse(response, headers));

        } catch (IOException e) { // Service is unavailable

            // Handle and throw exception
            throw handleServiceUnAvailableException(headers, e);
        }

    }

    @Override
    protected void storeSubTransactionInDB(ClientOwnerRequest clientRequest, RequestHeaders headers) {

    }

    @Override
    protected BankCardOwnerRequest createBankRequest(ClientOwnerRequest clientRequest, RequestHeaders headers) {

        // Initial bankCardOwnerRequest
        BankCardOwnerRequest bankCardOwnerRequest = new BankCardOwnerRequest();
        bankCardOwnerRequest.setPan(clientRequest.getPan());
        bankCardOwnerRequest.setDestinationPan(clientRequest.getDestinationNumber());

        bankCardOwnerRequest.setCardAuthorizeParams(getCardAuthorizeParams(clientRequest.getEauth()));

        return bankCardOwnerRequest;
    }

    @Override
    protected Response<BankCardOwnerResponse> callService(BankCardOwnerRequest request, RequestHeaders requestHeaders) throws IOException {
        return CardClient.instance.owner(getBankRequestHeader(requestHeaders), request).execute();
    }

    @Override
    protected void updateSubTransaction(BankCardOwnerResponse response, Byte transactionStatus) {

    }

    @Override
    protected ClientOwnerResponse createClientResponse(BankCardOwnerResponse response) {
        ClientOwnerResponse clientOwnerResponse = new ClientOwnerResponse();
        clientOwnerResponse.setBankName(response.getOwnerBankName());
        clientOwnerResponse.setName(response.getFirstName() + " " + response.getLastName());
        clientOwnerResponse.setTransactionNumber(response.getTransactionNumber());

        return clientOwnerResponse;
    }

    @Override
    protected byte getTransactionType() {
        return TransactionType.CARD_OWNER.getValue();
    }
}
