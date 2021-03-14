package com.generify.service.card;

import com.generify.AppConfig;
import com.generify.Util.ParsianUtil;
import com.generify.client.card.model.CardAuthorizeParams;
import com.generify.client.card.model.IBankCardRequest;
import com.generify.client.card.model.IBankCardResponse;
import com.generify.client.model.IBankRequest;
import com.generify.model.IClientRequest;
import com.generify.model.PinType;
import com.generify.model.RequestHeaders;
import com.generify.model.card.ClientCardAuthorizeParams;
import com.generify.model.card.IClientCardRequest;
import com.generify.model.card.IClientCardResponse;
import com.generify.repository.TransactionRepository;
import com.generify.repository.log.LogRepository;
import com.generify.service.AbstractService;
import org.springframework.context.MessageSource;
import retrofit2.Response;

/**
 * Abstract class for call and handle card services. Common methods like a method for store request logs to db are implemented in this abstract class
 * and other specific methods like method for create client response are abstract and must be implemented in each sub service.
 *
 * <p>The workflow should be as follows:
 * <p>1- Create bankRequest with {@link AbstractCardService#createBankRequest(IClientCardRequest, RequestHeaders)}
 * <p>2- Call service of bank with {@link AbstractService#callService(IBankRequest, RequestHeaders)}
 * <p>3- Handle response of {@link AbstractService#callService(IBankRequest, RequestHeaders)} with {@link AbstractService#handleBankResponseAndLogReceivedResponse(Response, RequestHeaders)}
 * <p>4- Write necessary data to database with {@link AbstractService#storeTransactionInDB(RequestHeaders, String, Long, Long)}
 * <p>5- Create client response with {@link AbstractCardService#createClientResponse(IBankCardResponse)}
 * <p>6- Write client request/response and bank request/response to database with {@link AbstractService#storeClientRequestLog(IClientRequest, RequestHeaders)}
 * And {@link AbstractService#storeClientResponseLog(String, RequestHeaders)} (com.ada.parsian.parsianmobilebank.model.IClientResponse, RequestHeaders)} And {@link AbstractService#storeRequestSentToBankLog(IBankRequest, RequestHeaders)}
 * And {@link AbstractService#storeResponseReceivedFromBankLog(String, RequestHeaders)} (com.ada.parsian.parsianmobilebank.client.model.IBankResponse, RequestHeaders)}
 * <p>If an error occurred in {@link AbstractService#callService(IBankRequest, RequestHeaders)} then in method {@link AbstractService#handleError(Response, RequestHeaders)}
 * handle the error and throw exception
 *
 * @param <T> Body of client request and is of type {@link IClientCardRequest}
 * @param <K> Body of response sent to client and is of type {@link IClientCardResponse}
 * @param <L> Body of request sent to bank and is of type {@link IBankCardRequest}
 * @param <M> Body of response received from bank and is of type {@link IBankCardResponse}
 */

public abstract class AbstractCardService<T extends IClientCardRequest, K extends IClientCardResponse,
        L extends IBankCardRequest, M extends IBankCardResponse> extends AbstractService<T, K, L, M> {

    /**
     * In this constructor you must pass messageSource param. in the subclass you should define parameter
     * as follow:
     * <p>-- @Qualifier(value = "messageSource") MessageSource messageSource
     * <p></p> The "messageSource" is defined in {@link AppConfig#messageSource()}
     *
     * @param messageSource used for reading persian messages from messages.properties file.
     */
    public AbstractCardService(MessageSource messageSource, LogRepository logRepository, TransactionRepository transactionRepository, AppConfig appConfig) {
        super(messageSource, logRepository, transactionRepository, appConfig);
    }

    protected CardAuthorizeParams getCardAuthorizeParams(ClientCardAuthorizeParams params) {

        CardAuthorizeParams cardAuthorizeParams = new CardAuthorizeParams();
        cardAuthorizeParams.setCvv2(params.getCvv2());
        cardAuthorizeParams.setPin(params.getPin());
        cardAuthorizeParams.setExpireDate(params.getExpDate());
        cardAuthorizeParams.setPinType(PinType.EPAY.value());
        return cardAuthorizeParams;
    }

    protected CardAuthorizeParams getCardAuthorizeParams(String eauth) {

        CardAuthorizeParams cardAuthorizeParams = ParsianUtil.decrypt(eauth, appConfig.getEncryptionParam().getSecretKey());
        cardAuthorizeParams.setPinType(PinType.EPAY.value());
        return cardAuthorizeParams;
    }
}
