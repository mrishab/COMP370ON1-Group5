package io.trishul.classplanner.network;

import io.trishul.classplanner.network.dtos.UserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface UserApi {
    @GET("api/v1/users/me")
    Call<UserDTO.Get> getCurrentUser();

    @PUT("api/v1/users/me")
    Call<UserDTO.Get> updateCurrentUser(@Body UserDTO.Put user);

    @DELETE("api/v1/users/me")
    Call<Void> deleteCurrentUser();
}
