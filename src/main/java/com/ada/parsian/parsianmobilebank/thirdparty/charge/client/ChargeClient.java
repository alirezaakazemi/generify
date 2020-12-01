package com.ada.parsian.parsianmobilebank.thirdparty.charge.client;

import com.ada.parsian.parsianmobilebank.AppConfig;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardPaymentRequest;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardPaymentResponse;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model.ChargeAvailabilityRequest;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model.ChargeAvailabilityResponse;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model.ChargeRequest;
import com.ada.parsian.parsianmobilebank.thirdparty.charge.client.model.ChargeResponse;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.tomcat.util.codec.binary.Base64;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Map;

public class ChargeClient {

    public static ChargeClient instance = new ChargeClient();
    private IChargeClient client;

    public void init(AppConfig.ChargeServiceConfig chargeServiceConfig) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        String credentials = chargeServiceConfig.getUsername() + ":" + chargeServiceConfig.getPassword(); // Charge test

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(chargeServiceConfig.getEndpoint())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("Authorization", "Basic " + new Base64().encodeAsString(credentials.getBytes()))
                                        .build();
                                return chain.proceed(request);
                            }
                        })
                        .addInterceptor(interceptor)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        client = retrofit.create(IChargeClient.class);
    }

    public Call<ChargeAvailabilityResponse> chargeAvailability(ChargeAvailabilityRequest request) {
        return instance.client.chargeAvailability(request);
    }

    public Call<ChargeResponse> charge(ChargeRequest chargeRequest) {
        return instance.client.charge(chargeRequest);
    }

    public Call<BankCardPaymentResponse> payment(Map<String, String> headers, BankCardPaymentRequest request) {
        return instance.client.payment(headers, request);
    }
}
