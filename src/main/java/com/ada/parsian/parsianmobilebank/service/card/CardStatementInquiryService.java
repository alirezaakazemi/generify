package com.ada.parsian.parsianmobilebank.service.card;

import com.ada.parsian.parsianmobilebank.AppConfig;
import com.ada.parsian.parsianmobilebank.client.card.CardClient;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardStatementInquiryRequest;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardStatementInquiryResponse;
import com.ada.parsian.parsianmobilebank.model.RequestHeaders;
import com.ada.parsian.parsianmobilebank.model.SourceType;
import com.ada.parsian.parsianmobilebank.model.TransactionType;
import com.ada.parsian.parsianmobilebank.model.card.ClientCardStatementInquiryRequest;
import com.ada.parsian.parsianmobilebank.model.card.ClientCardStatementInquiryResponse;
import com.ada.parsian.parsianmobilebank.repository.TransactionRepository;
import com.ada.parsian.parsianmobilebank.repository.log.LogRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

@Service
public class CardStatementInquiryService extends AbstractCardService<ClientCardStatementInquiryRequest, ClientCardStatementInquiryResponse,
        BankCardStatementInquiryRequest, BankCardStatementInquiryResponse> {

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
    public CardStatementInquiryService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository) {
        super(messageSource, logRepository, transactionRepository);
    }

    @Override
    public ClientCardStatementInquiryResponse execute(RequestHeaders headers, ClientCardStatementInquiryRequest clientRequest) {

        try {

            // Store transaction with initial data
            transaction = storeTransactionInDB(headers, clientRequest.getPan(), SourceType.CARD.getValue(), null);

            // Store clientRequest in db
            storeClientRequestLog(clientRequest, headers);

            // Initial bankCardStatementInquiryRequest
            BankCardStatementInquiryRequest bankCardStatementInquiryRequest = createBankRequest(clientRequest, headers);

            // Store bankRequest body in log db
            storeRequestSentToBankLog(bankCardStatementInquiryRequest, headers);

            // Call statementInquiry service
            Response<BankCardStatementInquiryResponse> response = callService(bankCardStatementInquiryRequest, headers);

            // Handle cardStatementInquiry response
            return createClientResponse(handleBankResponseAndLogReceivedResponse(response, headers));

        } catch (IOException e) { // Service is unavailable

            // Handle and throw exception
            throw handleServiceUnAvailableException(headers, e);
        }
    }

    @Override
    protected void storeSubTransactionInDB(ClientCardStatementInquiryRequest clientRequest) {

    }

    @Override
    protected BankCardStatementInquiryRequest createBankRequest(ClientCardStatementInquiryRequest clientRequest, RequestHeaders headers) {

        BankCardStatementInquiryRequest bankRequest = new BankCardStatementInquiryRequest();
        bankRequest.setDepositNumber(clientRequest.getDepositNumber());
        bankRequest.setPan(clientRequest.getPan());

        bankRequest.setCardAuthorizeParams(getCardAuthorizeParams(clientRequest.getCardAuthorizeParams()));

        return bankRequest;
    }

    @Override
    protected Response<BankCardStatementInquiryResponse> callService(BankCardStatementInquiryRequest request, RequestHeaders requestHeaders) throws IOException {
        return CardClient.instance.statementInquiry(getBankRequestHeader(requestHeaders), request).execute();
    }

    @Override
    protected void updateSubTransaction(BankCardStatementInquiryResponse response, Byte transactionStatus) {

    }

    @Override
    protected ClientCardStatementInquiryResponse createClientResponse(BankCardStatementInquiryResponse response) {

        return new ClientCardStatementInquiryResponse(response.getCardStatements());
    }

    @Override
    protected byte getTransactionType() {
        return TransactionType.CARD_STATEMENT.getValue();
    }
}
