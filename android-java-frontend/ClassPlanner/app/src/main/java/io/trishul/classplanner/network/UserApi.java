package io.trishul.classplanner.network;

import io.trishul.classplanner.network.dtos.UserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserApi {
    @POST("api/v1/users/login")
    Call<UserDTO.Get> login(@Body UserDTO.Login request);

    @GET("api/v1/users/me")
    Call<UserDTO.Get> getCurrentUser();

    @POST("api/v1/users/register")
    Call<UserDTO.Get> register(@Body UserDTO.Post user);

    @PUT("api/v1/users/me")
    Call<UserDTO.Get> updateCurrentUser(@Body UserDTO.Put user);

    @DELETE("api/v1/users/me")
    Call<Void> deleteCurrentUser();
}
