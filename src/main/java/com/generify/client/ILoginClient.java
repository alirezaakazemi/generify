package com.generify.client;

import com.generify.client.model.LoginRequest;
import com.generify.client.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ILoginClient {

    @POST("/api/mobilebankSandbox/1.0/mobileBankLogin")
    Call<LoginResponse> login(@Body LoginRequest request);
}
