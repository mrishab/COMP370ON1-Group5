package io.trishul.classplanner.network.api;

import io.trishul.classplanner.network.dtos.UserDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Body;

public interface UserApi {
    @GET("users/me")
    Call<UserDTO> getCurrentUser();
    
    @PUT("users/me")
    Call<UserDTO> updateCurrentUser(@Body UserDTO userDTO);
}