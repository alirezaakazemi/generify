package com.generify.service.card;

import com.generify.AppConfig;
import com.generify.client.card.CardClient;
import com.generify.client.card.model.BankCardStatementInquiryRequest;
import com.generify.client.card.model.BankCardStatementInquiryResponse;
import com.generify.model.RequestHeaders;
import com.generify.model.SourceType;
import com.generify.model.TransactionType;
import com.generify.model.card.ClientCardStatementInquiryRequest;
import com.generify.model.card.ClientCardStatementInquiryResponse;
import com.generify.repository.TransactionRepository;
import com.generify.repository.log.LogRepository;
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
    public CardStatementInquiryService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository, AppConfig appConfig) {
        super(messageSource, logRepository, transactionRepository, appConfig);
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
    protected void storeSubTransactionInDB(ClientCardStatementInquiryRequest clientRequest, RequestHeaders headers) {

    }

    @Override
    protected BankCardStatementInquiryRequest createBankRequest(ClientCardStatementInquiryRequest clientRequest, RequestHeaders headers) {

        BankCardStatementInquiryRequest bankRequest = new BankCardStatementInquiryRequest();
        bankRequest.setDepositNumber(clientRequest.getDepositNumber());
        bankRequest.setPan(clientRequest.getPan());

        bankRequest.setCardAuthorizeParams(getCardAuthorizeParams(clientRequest.getEauth()));

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
