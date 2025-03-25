package io.trishul.classplanner.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("/api/login")
    Call<Void> login(@Body LoginRequest loginRequest);

    @POST("/api/register")
    Call<Void> register(@Body RegisterRequest registerRequest);
}