package com.ada.parsian.parsianmobilebank.service.card;

import com.ada.parsian.parsianmobilebank.AppConfig;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardPaymentRequest;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardPaymentResponse;
import com.ada.parsian.parsianmobilebank.model.RequestHeaders;
import com.ada.parsian.parsianmobilebank.model.SourceType;
import com.ada.parsian.parsianmobilebank.model.TransactionType;
import com.ada.parsian.parsianmobilebank.model.card.ClientCardChargeRequest;
import com.ada.parsian.parsianmobilebank.model.card.ClientCardChargeResponse;
import com.ada.parsian.parsianmobilebank.repository.TransactionRepository;
import com.ada.parsian.parsianmobilebank.repository.card.ChargeRepository;
import com.ada.parsian.parsianmobilebank.repository.entity.Charge;
import com.ada.parsian.parsianmobilebank.repository.log.LogRepository;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.ChargeClient;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.command.ChargeAvailabilityCommand;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.command.ChargeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

@Service
public class CardChargeService extends AbstractCardService<ClientCardChargeRequest, ClientCardChargeResponse, BankCardPaymentRequest, BankCardPaymentResponse> {

    @Autowired
    private ChargeRepository chargeRepository;
    private Charge charge;

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
    public CardChargeService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository, ChargeRepository chargeRepository, AppConfig appConfig) {
        super(messageSource, logRepository, transactionRepository, appConfig);
        this.chargeRepository = chargeRepository;
    }

    @Override
    public ClientCardChargeResponse execute(RequestHeaders headers, ClientCardChargeRequest clientRequest) {

        try {

            // Store transaction with initial data
            transaction = storeTransactionInDB(headers, clientRequest.getPan(), SourceType.CARD.getValue(), clientRequest.getAmount());

            // Store transaction with initial data
            storeClientRequestLog(clientRequest, headers);

            // Store Charge in db
            storeSubTransactionInDB(clientRequest);

            // Reserve charge
            charge = reserveCharge(clientRequest, headers);

            // Do payment
            BankCardPaymentResponse paymentResponse = doPayment(headers, clientRequest);

            // Charge setDestinationMobile number
            charge = doCharge(clientRequest, headers);

            // Create and return response
            return createClientResponse(paymentResponse);

        } catch (IOException e) {

            // Handle and throw exception
            throw handleServiceUnAvailableException(headers, e);
        }
    }

    private BankCardPaymentResponse doPayment(RequestHeaders headers, ClientCardChargeRequest clientRequest) throws IOException {

        // Initial bankPaymentRequest
        BankCardPaymentRequest bankCardPaymentRequest = createBankRequest(clientRequest, headers);

        // Store bankRequest body in log db
        storeRequestSentToBankLog(bankCardPaymentRequest, headers);

        // Call payment service
        Response<BankCardPaymentResponse> response = callService(bankCardPaymentRequest, headers);

        // Handle cardPayment response
        return handleBankResponseAndLogReceivedResponse(response, headers);
    }

    private Charge reserveCharge(ClientCardChargeRequest clientRequest, RequestHeaders headers) {

        // Initial chargeAvailabilityCommand
        ChargeAvailabilityCommand chargeAvailabilityCommand = new ChargeAvailabilityCommand(messageSource, transaction, logRepository, transactionRepository,
                headers, charge, chargeRepository, TransactionType.CHARGE.getValue());

        // Reserve charge and return
        return chargeAvailabilityCommand.execute(clientRequest);
    }

    private Charge doCharge(ClientCardChargeRequest clientRequest, RequestHeaders headers) {

        // Initial chargeCommand
        ChargeCommand chargeCommand = new ChargeCommand(messageSource, transaction, logRepository, transactionRepository, headers, charge,
                chargeRepository, TransactionType.CHARGE.getValue());

        // Charge phone number
        return chargeCommand.execute(clientRequest);
    }

    @Override
    protected void storeSubTransactionInDB(ClientCardChargeRequest clientRequest) {

        Charge charge = new Charge();
        charge.setDestinationMobile(clientRequest.getDestPhoneNumber());
        charge.setProductId(Long.parseLong(clientRequest.getProductId()));
        charge.setTransactionId(transaction.getId());

        this.charge = chargeRepository.save(charge);
    }

    @Override
    protected BankCardPaymentRequest createBankRequest(ClientCardChargeRequest clientRequest, RequestHeaders headers) {
        BankCardPaymentRequest bankCardPaymentRequest = new BankCardPaymentRequest();
        bankCardPaymentRequest.setAmount(clientRequest.getAmount());

        bankCardPaymentRequest.setCardAuthorizeParams(getCardAuthorizeParams(clientRequest.getEauth()));

        return bankCardPaymentRequest;
    }

    @Override
    protected Response<BankCardPaymentResponse> callService(BankCardPaymentRequest request, RequestHeaders requestHeaders) throws IOException {
//        return CardClient.instance.payment(getBankRequestHeader(requestHeaders), request).execute();
        return ChargeClient.instance.payment(getBankRequestHeader(requestHeaders), request).execute();
    }

    @Override
    protected void updateSubTransaction(BankCardPaymentResponse response, Byte transactionStatus) {

        charge.setPaymentStatus(transactionStatus);
        charge = chargeRepository.save(charge);
    }

    @Override
    protected ClientCardChargeResponse createClientResponse(BankCardPaymentResponse response) {

        ClientCardChargeResponse clientCardChargeResponse = new ClientCardChargeResponse();
        clientCardChargeResponse.setTransactionNumber(String.valueOf(transaction.getId()));
        clientCardChargeResponse.setTransactionDate(transaction.getTransactionDate().getTime());
        clientCardChargeResponse.setAvailableBalance(response.getAmountBean().getAvailableBalance().getValue());
        clientCardChargeResponse.setChargeStatus(response.getAmountBean().getAvailableBalance().getCurrency());

        return clientCardChargeResponse;
    }

    @Override
    protected byte getTransactionType() {
        return TransactionType.CHARGE.getValue();
    }
}
