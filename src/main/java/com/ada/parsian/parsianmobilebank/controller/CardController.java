package com.ada.parsian.parsianmobilebank.controller;

import com.ada.parsian.parsianmobilebank.model.card.*;
import com.ada.parsian.parsianmobilebank.service.card.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(path = "/card")
public class CardController extends BaseController {

    private final CardOwnerService cardOwnerService;
    private final CardTransferService cardTransferService;
    private final CardPayBillService cardPayBillService;
    private final CardBalanceInquiryService cardBalanceInquiryService;
    private final CardStatementInquiryService cardStatementInquiryService;
    private final CardDepositsService cardDepositsService;
    private final GetCardsService getCardsService;
    private final CardChargeService cardChargeService;

    @Autowired
    public CardController(CardOwnerService cardOwnerService, CardTransferService cardTransferService, CardPayBillService cardPayBillService,
                          CardBalanceInquiryService cardBalanceInquiryService, CardStatementInquiryService cardStatementInquiryService,
                          CardDepositsService cardDepositsService, GetCardsService getCardsService, CardChargeService cardChargeService) {
        this.cardOwnerService = cardOwnerService;
        this.cardTransferService = cardTransferService;
        this.cardPayBillService = cardPayBillService;
        this.cardBalanceInquiryService = cardBalanceInquiryService;
        this.cardStatementInquiryService = cardStatementInquiryService;
        this.cardDepositsService = cardDepositsService;
        this.getCardsService = getCardsService;
        this.cardChargeService = cardChargeService;
    }

    @PostMapping(path = "/owner")
    public ClientCardOwnerResponse owner(@RequestHeader Map<String, String> requestHeaders, HttpServletRequest servletRequest, @RequestBody ClientCardOwnerRequest request) {
        return cardOwnerService.execute(getHeaders(requestHeaders, servletRequest), request);
    }

    @PostMapping(path = "/transfer")
    public ClientCardTransferResponse transfer(@RequestHeader Map<String, String> requestHeaders, HttpServletRequest servletRequest, @RequestBody ClientCardTransferRequest request) {
        return cardTransferService.execute(getHeaders(requestHeaders, servletRequest), request);
    }

    @PostMapping(path = "/balanceInquiry")
    public ClientCardBalanceInquiryResponse balanceInquiry(@RequestHeader Map<String, String> requestHeaders, HttpServletRequest servletRequest, @RequestBody ClientCardBalanceInquiryRequest request) {
        return cardBalanceInquiryService.execute(getHeaders(requestHeaders, servletRequest), request);
    }

    @PostMapping(path = "/statementInquiry")
    public ClientCardStatementInquiryResponse statementInquiry(@RequestHeader Map<String, String> requestHeaders, HttpServletRequest servletRequest, @RequestBody ClientCardStatementInquiryRequest request) {
        return cardStatementInquiryService.execute(getHeaders(requestHeaders, servletRequest), request);
    }

    @PostMapping(path = "/payBill")
    public ClientCardPayBillResponse payBill(@RequestHeader Map<String, String> requestHeaders, HttpServletRequest servletRequest, @RequestBody ClientCardPayBillRequest request) {
        return cardPayBillService.execute(getHeaders(requestHeaders, servletRequest), request);
    }

    @PostMapping(path = "/deposits")
    public ClientCardDepositsResponse deposits(@RequestHeader Map<String, String> requestHeaders, HttpServletRequest servletRequest, @RequestBody ClientCardDepositsRequest request) {
        return cardDepositsService.execute(getHeaders(requestHeaders, servletRequest), request);
    }

    @PostMapping(path = "/cards")
    public ClientGetCardsResponse cards(@RequestHeader Map<String, String> requestHeaders, HttpServletRequest servletRequest, @RequestBody ClientGetCardsRequest request) {
        return getCardsService.execute(getHeaders(requestHeaders, servletRequest), request);
    }

    @PostMapping(path = "/charge")
    public ClientCardChargeResponse charge(@RequestHeader Map<String, String> requestHeaders, HttpServletRequest servletRequest, @RequestBody ClientCardChargeRequest request) {
        return cardChargeService.execute(getHeaders(requestHeaders, servletRequest), request);
    }
}
