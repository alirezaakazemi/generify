package com.ada.parsian.parsianmobilebank.client.card;

import com.ada.parsian.parsianmobilebank.client.card.model.*;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Map;

public class CardClient {

    public static CardClient instance = new CardClient();
    private ICardClient client;

    public void init(String endpoint) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        client = retrofit.create(ICardClient.class);
    }

    public Call<BankCardOwnerResponse> owner(Map<String, String> headers, BankCardOwnerRequest request) {
        return instance.client.owner(headers, request);
    }

    public Call<BankCardTransferResponse> transfer(Map<String, String> headers, BankCardTransferRequest request) {
        return instance.client.transfer(headers, request);
    }

    public Call<BankCardBalanceInquiryResponse> balanceInquiry(Map<String, String> headers, BankCardBalanceInquiryRequest request) {
        return instance.client.balanceInquiry(headers, request);
    }

    public Call<BankCardStatementInquiryResponse> statementInquiry(Map<String, String> headers, BankCardStatementInquiryRequest request) {
        return instance.client.statementInquiry(headers, request);
    }

    public Call<BankCardPayBillResponse> payBill(Map<String, String> headers, BankCardPayBillRequest request) {
        return instance.client.payBill(headers, request);
    }

    public Call<BankCardPaymentResponse> payment(Map<String, String> headers, BankCardPaymentRequest request) {
        return instance.client.payment(headers, request);
    }

    public Call<BankCardDepositsResponse> deposits(Map<String, String> headers, BankCardDepositsRequest request) {
        return instance.client.deposits(headers, request);
    }

    public Call<Void> hotCard(Map<String, String> headers, BankHotCardRequest request) {
        return instance.client.hotCard(headers, request);
    }

    public Call<BankGetCardsResponse> getCards(Map<String, String> headers, BankGetCardsRequest request) {
        return instance.client.getCards(headers, request);
    }
}
