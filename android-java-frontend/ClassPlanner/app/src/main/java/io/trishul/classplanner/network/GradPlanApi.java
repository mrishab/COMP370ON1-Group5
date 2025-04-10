package io.trishul.classplanner.network;

import java.util.List;
import io.trishul.classplanner.network.dtos.GradPlanDTO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GradPlanApi {
    @GET("api/v1/gradplans")
    Call<List<GradPlanDTO.Get>> getPlans();

    @GET("api/v1/gradplans/{id}")
    Call<GradPlanDTO.Get> getPlan(@Path("id") Long id);

    @Multipart
    @POST("api/v1/gradplans")
    Call<GradPlanDTO.Get> createPlan(
        @Part("file-name") RequestBody fileName,
        @Part MultipartBody.Part file
    );

    @DELETE("api/v1/gradplans")
    Call<Void> deletePlans(@Query("ids") List<Long> ids);

    @GET("api/v1/gradplans/archived")
    Call<List<GradPlanDTO.Get>> getArchivedPlans();

    @PUT("api/v1/gradplans/archived")
    Call<Void> activatePlans(@Query("ids") List<Long> ids);
}
