package com.generify.client.card;

import com.generify.client.card.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

import java.util.Map;

public interface ICardClient {

    @POST("/api/mobilebankSandbox/1.0/getCardOwner")
    Call<BankCardOwnerResponse> owner(@HeaderMap Map<String, String> headers, @Body BankCardOwnerRequest request);

    @POST("/api/mobilebankSandbox/1.0/cardTransfer")
    Call<BankCardTransferResponse> transfer(@HeaderMap Map<String, String> headers, @Body BankCardTransferRequest request);

    @POST("/api/mobilebankSandbox/1.0/cardBalanceInquiry")
    Call<BankCardBalanceInquiryResponse> balanceInquiry(@HeaderMap Map<String, String> headers, @Body BankCardBalanceInquiryRequest request);

    @POST("/api/mobilebankSandbox/1.0/getCardStatementInquiry")
    Call<BankCardStatementInquiryResponse> statementInquiry(@HeaderMap Map<String, String> headers, @Body BankCardStatementInquiryRequest request);

    @POST("/api/mobilebankSandbox/1.0/payBill")
    Call<BankCardPayBillResponse> payBill(@HeaderMap Map<String, String> headers, @Body BankCardPayBillRequest request);

    //    @POST("/api/mobilebankSandbox/1.0/cardPurchase")
    Call<BankCardPaymentResponse> payment(@HeaderMap Map<String, String> headers, @Body BankCardPaymentRequest request);

    @POST("/api/mobilebankSandbox/1.0/getCardDeposits")
    Call<BankCardDepositsResponse> deposits(@HeaderMap Map<String, String> headers, @Body BankCardDepositsRequest request);

    @POST("/api/mobilebankSandbox/1.0/hotCard")
    Call<Void> hotCard(@HeaderMap Map<String, String> headers, @Body BankHotCardRequest request);

    @POST("/api/mobilebankSandbox/1.0/getCards")
    Call<BankGetCardsResponse> getCards(@HeaderMap Map<String, String> headers, @Body BankGetCardsRequest request);
}
