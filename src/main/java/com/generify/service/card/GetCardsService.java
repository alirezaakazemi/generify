package com.generify.service.card;

import com.generify.AppConfig;
import com.generify.client.card.CardClient;
import com.generify.client.card.model.BankGetCardsRequest;
import com.generify.client.card.model.BankGetCardsResponse;
import com.generify.model.RequestHeaders;
import com.generify.model.SourceType;
import com.generify.model.TransactionType;
import com.generify.model.card.ClientGetCardsRequest;
import com.generify.model.card.ClientGetCardsResponse;
import com.generify.repository.TransactionRepository;
import com.generify.repository.log.LogRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

@Service
public class GetCardsService extends AbstractCardService<ClientGetCardsRequest, ClientGetCardsResponse, BankGetCardsRequest, BankGetCardsResponse> {

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
    public GetCardsService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository, AppConfig appConfig) {
        super(messageSource, logRepository, transactionRepository, appConfig);
    }

    @Override
    public ClientGetCardsResponse execute(RequestHeaders headers, ClientGetCardsRequest clientRequest) {
        try {

            // Store transaction with initial data
            transaction = storeTransactionInDB(headers, null, SourceType.CARD.getValue(), null);

            // Store clientRequest in db
            storeClientRequestLog(clientRequest, headers);

            // Initial bankOwnerRequest
            BankGetCardsRequest bankCardDepositsRequest = createBankRequest(clientRequest, headers);

            // Store bankRequest body in log db
            storeRequestSentToBankLog(bankCardDepositsRequest, headers);

            // Call owner service
            Response<BankGetCardsResponse> response = callService(bankCardDepositsRequest, headers);

            // Handle cardDeposits response
            return createClientResponse(handleBankResponseAndLogReceivedResponse(response, headers));

        } catch (IOException e) { // Service is unavailable

            // Handle and throw exception
            throw handleServiceUnAvailableException(headers, e);
        }

    }

    @Override
    protected void storeSubTransactionInDB(ClientGetCardsRequest clientRequest, RequestHeaders headers) {

    }

    @Override
    protected BankGetCardsRequest createBankRequest(ClientGetCardsRequest clientRequest, RequestHeaders headers) {
        BankGetCardsRequest bankGetCardsRequest = new BankGetCardsRequest();
        bankGetCardsRequest.setCif(clientRequest.getCif());
        bankGetCardsRequest.setCardStatus(clientRequest.getCardStatus());
        bankGetCardsRequest.setLength(10);
        bankGetCardsRequest.setOffset(clientRequest.getOffset());
        return bankGetCardsRequest;
    }

    @Override
    protected Response<BankGetCardsResponse> callService(BankGetCardsRequest request, RequestHeaders requestHeaders) throws IOException {
        return CardClient.instance.getCards(getBankRequestHeader(requestHeaders), request).execute();
    }

    @Override
    protected void updateSubTransaction(BankGetCardsResponse response, Byte transactionStatus) {

    }

    @Override
    protected ClientGetCardsResponse createClientResponse(BankGetCardsResponse response) {
        ClientGetCardsResponse clientGetCardsResponse = new ClientGetCardsResponse();
        clientGetCardsResponse.setCardBeans(response.getCardBeans());

        return clientGetCardsResponse;
    }

    @Override
    protected byte getTransactionType() {
        return TransactionType.GET_CARDS.getValue();
    }
}
