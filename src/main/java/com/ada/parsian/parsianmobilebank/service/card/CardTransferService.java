package com.ada.parsian.parsianmobilebank.service.card;

import com.ada.parsian.parsianmobilebank.AppConfig;
import com.ada.parsian.parsianmobilebank.Util.ParsianUtil;
import com.ada.parsian.parsianmobilebank.client.card.CardClient;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardTransferRequest;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardTransferResponse;
import com.ada.parsian.parsianmobilebank.model.*;
import com.ada.parsian.parsianmobilebank.model.card.ClientCardTransferRequest;
import com.ada.parsian.parsianmobilebank.model.card.ClientCardTransferResponse;
import com.ada.parsian.parsianmobilebank.repository.TransactionRepository;
import com.ada.parsian.parsianmobilebank.repository.card.CardRepository;
import com.ada.parsian.parsianmobilebank.repository.entity.CardTransfer;
import com.ada.parsian.parsianmobilebank.repository.entity.Transaction;
import com.ada.parsian.parsianmobilebank.repository.log.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;

@Service
public class CardTransferService extends AbstractCardService<ClientCardTransferRequest, ClientCardTransferResponse, BankCardTransferRequest, BankCardTransferResponse> {

    @Autowired
    CardRepository cardRepository;

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
    public CardTransferService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository, CardRepository cardRepository) {
        super(messageSource, logRepository, transactionRepository);
        this.cardRepository = cardRepository;
    }

    @Override
    public ClientCardTransferResponse execute(RequestHeaders headers, ClientCardTransferRequest clientRequest) {

        try {
            // Store transaction with initial data
            transaction = storeTransactionInDB(headers, clientRequest.getPan(), SourceType.CARD.getValue(), clientRequest.getAmount());

            // Store clientRequest in db
            storeClientRequestLog(clientRequest, headers);

            // Store cardTransfer in db
            storeSubTransactionInDB(clientRequest);

            // Initial bankTransferRequest
            BankCardTransferRequest bankCardTransferRequest = createBankRequest(clientRequest, headers);

            // Store bankRequest body in log db
            storeRequestSentToBankLog(bankCardTransferRequest, headers);

            // Call transfer service
            Response<BankCardTransferResponse> response = callService(bankCardTransferRequest, headers);

            // Handle cardTransfer response
            return createClientResponse(handleBankResponseAndLogReceivedResponse(response, headers));

        } catch (IOException e) { // Service is unavailable

            // Handle and throw exception
            throw handleServiceUnAvailableException(headers, e);
        }

    }

    @Override
    protected void storeSubTransactionInDB(ClientCardTransferRequest clientRequest) {
        CardTransfer cardTransfer = new CardTransfer();
        cardTransfer.setTransactionId(transaction.getId());
        cardTransfer.setDestination(clientRequest.getDestinationPan());
        cardTransfer.setDestinationType(DestinationType.CARD.getValue());

        cardRepository.save(cardTransfer);
    }

    @Override
    protected BankCardTransferRequest createBankRequest(ClientCardTransferRequest clientRequest, RequestHeaders headers) {

        BankCardTransferRequest bankCardTransferRequest = new BankCardTransferRequest();
        bankCardTransferRequest.setAmount(clientRequest.getAmount());
        bankCardTransferRequest.setCvv2(clientRequest.getCardAuthorizeParams().getCvv2());
        bankCardTransferRequest.setPin(clientRequest.getCardAuthorizeParams().getPin());
        bankCardTransferRequest.setPinType(PinType.EPAY.value());
        bankCardTransferRequest.setExpDate(clientRequest.getCardAuthorizeParams().getExpDate());
        bankCardTransferRequest.setDestination(clientRequest.getDestinationPan());
        bankCardTransferRequest.setDestinationType(TransferDestinationType.PAN.getValue());
        bankCardTransferRequest.setPan(clientRequest.getPan());
        bankCardTransferRequest.setTransactionNumber(clientRequest.getTransactionNumber());
        bankCardTransferRequest.setMobileNo(headers.getMobileNumber());

        return bankCardTransferRequest;
    }

    @Override
    protected Response<BankCardTransferResponse> callService(BankCardTransferRequest request, RequestHeaders requestHeaders) throws IOException {
        return CardClient.instance.transfer(getBankRequestHeader(requestHeaders), request).execute();
    }

    @Override
    protected Transaction updateSuccessTransaction(Timestamp transactionDate, BankCardTransferResponse response) {
        // Update transaction
        return updateTransactionInDB("200", TransactionStatus.SUCCESS.getValue(), ParsianUtil.getDateTimeFromPattern(response.getTransactionDate()),
                null, null, null, null);

    }

    @Override
    protected void updateSubTransaction(BankCardTransferResponse response, Byte transactionStatus) {

    }

    @Override
    protected ClientCardTransferResponse createClientResponse(BankCardTransferResponse response) {
        ClientCardTransferResponse clientCardTransferResponse = new ClientCardTransferResponse();
        clientCardTransferResponse.setAvailableBalance(response.getDeposit().getAvailableBalance().getValue());
        clientCardTransferResponse.setCurrency(response.getDeposit().getAvailableBalance().getCurrency());
        clientCardTransferResponse.setLedgerBalance(response.getDeposit().getLedgerBalance().getValue());
        Timestamp dateTimeFromPattern = ParsianUtil.getDateTimeFromPattern(response.getTransactionDate());
        clientCardTransferResponse.setTransactionDate(dateTimeFromPattern.getTime());
        clientCardTransferResponse.setTransactionNumber(response.getTransactionNumber());

        return clientCardTransferResponse;
    }

    @Override
    protected byte getTransactionType() {
        return TransactionType.CARD_TRANSFER.getValue();
    }
}
