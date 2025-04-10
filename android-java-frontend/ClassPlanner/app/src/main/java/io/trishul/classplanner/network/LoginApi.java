package io.trishul.classplanner.network;

import io.trishul.classplanner.network.dtos.UserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("api/v1/users/login")
    Call<UserDTO.Get> login(@Body UserDTO.Login request);

    @POST("api/v1/users/register")
    Call<UserDTO.Get> register(@Body UserDTO.Post user);
}
