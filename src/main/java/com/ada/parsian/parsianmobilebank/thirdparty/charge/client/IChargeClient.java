package com.ada.parsian.parsianmobilebank.thirdparty.charge.client;

import com.ada.parsian.parsianmobilebank.client.card.model.BankCardPaymentRequest;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardPaymentResponse;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model.ChargeAvailabilityRequest;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model.ChargeAvailabilityResponse;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model.ChargeRequest;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model.ChargeResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

import java.util.Map;

public interface IChargeClient {

    //    @POST("/chargeserver/availability")
    @POST("/bankette/chargeAvailability")
    Call<ChargeAvailabilityResponse> chargeAvailability(@Body ChargeAvailabilityRequest request);

    //    @POST("/chargeserver/charge")
    @POST("/bankette/charge")
    Call<ChargeResponse> charge(@Body ChargeRequest request);

    @POST("/bankette/payment")
    Call<BankCardPaymentResponse> payment(@HeaderMap Map<String, String> headers, @Body BankCardPaymentRequest request);
}