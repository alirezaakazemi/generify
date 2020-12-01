package com.ada.parsian.parsianmobilebank.client;

import com.ada.parsian.parsianmobilebank.client.model.LoginRequest;
import com.ada.parsian.parsianmobilebank.client.model.LoginResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginClient {

    public static LoginClient instance = new LoginClient();
    private ILoginClient client;

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

        client = retrofit.create(ILoginClient.class);
    }

    public Call<LoginResponse> login(LoginRequest request) {
        return instance.client.login(request);
    }
}
