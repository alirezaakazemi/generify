package com.ada.parsian.parsianmobilebank.service.card;

import com.ada.parsian.parsianmobilebank.AppConfig;
import com.ada.parsian.parsianmobilebank.Util.ParsianUtil;
import com.ada.parsian.parsianmobilebank.client.card.CardClient;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardPayBillRequest;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardPayBillResponse;
import com.ada.parsian.parsianmobilebank.model.RequestHeaders;
import com.ada.parsian.parsianmobilebank.model.SourceType;
import com.ada.parsian.parsianmobilebank.model.TransactionType;
import com.ada.parsian.parsianmobilebank.model.card.ClientCardPayBillRequest;
import com.ada.parsian.parsianmobilebank.model.card.ClientCardPayBillResponse;
import com.ada.parsian.parsianmobilebank.repository.TransactionRepository;
import com.ada.parsian.parsianmobilebank.repository.card.PayBillRepository;
import com.ada.parsian.parsianmobilebank.repository.entity.PayBill;
import com.ada.parsian.parsianmobilebank.repository.log.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;

@Service
public class CardPayBillService extends AbstractCardService<ClientCardPayBillRequest, ClientCardPayBillResponse, BankCardPayBillRequest, BankCardPayBillResponse> {

    @Autowired
    PayBillRepository payBillRepository;

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
    public CardPayBillService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository, PayBillRepository payBillRepository, AppConfig appConfig) {
        super(messageSource, logRepository, transactionRepository, appConfig);
        this.payBillRepository = payBillRepository;
    }

    @Override
    public ClientCardPayBillResponse execute(RequestHeaders headers, ClientCardPayBillRequest clientRequest) {

        try {
            // Store transaction with initial data
            transaction = storeTransactionInDB(headers, clientRequest.getPan(), SourceType.CARD.getValue(), null);

            // Store clientRequest in db
            storeClientRequestLog(clientRequest, headers);

            // Store cardPayBill in db
            storeSubTransactionInDB(clientRequest, headers);

            // Initial bankPayBillRequest
            BankCardPayBillRequest bankCardPayBillRequest = createBankRequest(clientRequest, headers);

            // Store bankRequest body in log db
            storeRequestSentToBankLog(bankCardPayBillRequest, headers);

            // Call payBill service
            Response<BankCardPayBillResponse> response = callService(bankCardPayBillRequest, headers);

            // Handle cardPayBill response
            return createClientResponse(handleBankResponseAndLogReceivedResponse(response, headers));

        } catch (IOException e) { // Service is unavailable

            // Handle and throw exception
            throw handleServiceUnAvailableException(headers, e);
        }

    }

    @Override
    protected void storeSubTransactionInDB(ClientCardPayBillRequest clientRequest, RequestHeaders headers) {

        PayBill payBill = new PayBill();
        payBill.setBillId(clientRequest.getBillId());
        payBill.setPayId(clientRequest.getPayId());
        payBill.setTransactionId(transaction.getId());

        payBillRepository.save(payBill);
    }

    @Override
    protected BankCardPayBillRequest createBankRequest(ClientCardPayBillRequest clientRequest, RequestHeaders headers) {

        BankCardPayBillRequest bankCardPayBillRequest = new BankCardPayBillRequest();
        bankCardPayBillRequest.setBillId(clientRequest.getBillId());
        bankCardPayBillRequest.setPayId(clientRequest.getPayId());
        bankCardPayBillRequest.setPan(clientRequest.getPan());

        bankCardPayBillRequest.setCardAuthorizeParams(getCardAuthorizeParams(clientRequest.getEauth()));

        return bankCardPayBillRequest;
    }

    @Override
    protected Response<BankCardPayBillResponse> callService(BankCardPayBillRequest request, RequestHeaders requestHeaders) throws IOException {
        return CardClient.instance.payBill(getBankRequestHeader(requestHeaders), request).execute();
    }

    @Override
    protected void updateSubTransaction(BankCardPayBillResponse response, Byte transactionStatus) {

    }

    @Override
    protected ClientCardPayBillResponse createClientResponse(BankCardPayBillResponse response) {

        ClientCardPayBillResponse clientCardPayBillResponse = new ClientCardPayBillResponse();
        Timestamp dateTimeFromPattern = ParsianUtil.getDateTimeFromPattern(response.getDate());
        clientCardPayBillResponse.setTransactionDate(dateTimeFromPattern.getTime());
        clientCardPayBillResponse.setBillType(response.getBillType());

        // TODO: 12/2/2020
//        clientCardPayBillResponse.setAvailableBalance(response.getBillType());

        return clientCardPayBillResponse;
    }

    @Override
    protected byte getTransactionType() {
        return TransactionType.PAY_BILL.getValue();
    }
}
