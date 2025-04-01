package io.trishul.classplanner.network;

import io.trishul.classplanner.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("/api/v1/users/login") 
    Call<User> login(@Body LoginRequest loginRequest);

    @POST("/api/v1/users/register") 
    Call<User> register(@Body RegisterRequest registerRequest);
}