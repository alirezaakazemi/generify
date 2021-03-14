package com.generify.service.card;

import com.generify.AppConfig;
import com.generify.client.card.CardClient;
import com.generify.client.card.model.BankCardBalanceInquiryRequest;
import com.generify.client.card.model.BankCardBalanceInquiryResponse;
import com.generify.model.RequestHeaders;
import com.generify.model.SourceType;
import com.generify.model.TransactionType;
import com.generify.model.card.ClientCardBalanceInquiryRequest;
import com.generify.model.card.ClientCardBalanceInquiryResponse;
import com.generify.repository.TransactionRepository;
import com.generify.repository.log.LogRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

@Service
public class CardBalanceInquiryService extends AbstractCardService<ClientCardBalanceInquiryRequest, ClientCardBalanceInquiryResponse,
        BankCardBalanceInquiryRequest, BankCardBalanceInquiryResponse> {

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
    public CardBalanceInquiryService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository, AppConfig appConfig) {
        super(messageSource, logRepository, transactionRepository, appConfig);
    }

    @Override
    public ClientCardBalanceInquiryResponse execute(RequestHeaders headers, ClientCardBalanceInquiryRequest clientRequest) {
        try {

            // Store transaction with initial data
            transaction = storeTransactionInDB(headers, clientRequest.getPan(), SourceType.CARD.getValue(), null);

            // Store clientRequest in db
            storeClientRequestLog(clientRequest, headers);

            // Initial bankCardBalanceInquiryRequest
            BankCardBalanceInquiryRequest bankCardBalanceInquiryRequest = createBankRequest(clientRequest, headers);

            // Store bankRequest body in log db
            storeRequestSentToBankLog(bankCardBalanceInquiryRequest, headers);

            // Call balanceInquiry service
            Response<BankCardBalanceInquiryResponse> response = callService(bankCardBalanceInquiryRequest, headers);

            // Handle cardBalanceInquiry response
            return createClientResponse(handleBankResponseAndLogReceivedResponse(response, headers));

        } catch (IOException e) { // Service is unavailable

            // Handle and throw exception
            throw handleServiceUnAvailableException(headers, e);
        }

    }

    @Override
    protected void storeSubTransactionInDB(ClientCardBalanceInquiryRequest clientRequest, RequestHeaders headers) {

    }

    @Override
    protected BankCardBalanceInquiryRequest createBankRequest(ClientCardBalanceInquiryRequest clientRequest, RequestHeaders headers) {

        BankCardBalanceInquiryRequest bankRequest = new BankCardBalanceInquiryRequest();
        bankRequest.setDepositNumber(clientRequest.getDepositNumber());
        bankRequest.setPan(clientRequest.getPan());

        bankRequest.setCardAuthorizeParams(getCardAuthorizeParams(clientRequest.getEauth()));

        return bankRequest;
    }

    @Override
    protected Response<BankCardBalanceInquiryResponse> callService(BankCardBalanceInquiryRequest request, RequestHeaders requestHeaders) throws IOException {
        return CardClient.instance.balanceInquiry(getBankRequestHeader(requestHeaders), request).execute();
    }

    @Override
    protected void updateSubTransaction(BankCardBalanceInquiryResponse response, Byte transactionStatus) {

    }

    @Override
    protected ClientCardBalanceInquiryResponse createClientResponse(BankCardBalanceInquiryResponse response) {

        ClientCardBalanceInquiryResponse clientCardBalanceInquiryResponse = new ClientCardBalanceInquiryResponse();
        clientCardBalanceInquiryResponse.setAvailableBalance(response.getAvailableBalance().getValue());
        clientCardBalanceInquiryResponse.setLedgerBalance(response.getLedgerBalance().getValue());

        return clientCardBalanceInquiryResponse;
    }

    @Override
    protected byte getTransactionType() {
        return TransactionType.CARD_BALANCE.getValue();
    }
}
