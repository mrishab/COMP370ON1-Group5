package io.trishul.classplanner.network;

import java.util.List;
import io.trishul.classplanner.network.dtos.ClassPlanDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClassPlanApi {
    @GET("api/v1/classplans")
    Call<List<ClassPlanDTO.Get>> getPlans();

    @GET("api/v1/classplans/{id}")
    Call<ClassPlanDTO.Get> getPlan(@Path("id") Long id);

    @POST("api/v1/classplans")
    Call<ClassPlanDTO.Get> createPlan(@Body ClassPlanDTO.Post classPlan);

    @DELETE("api/v1/classplans")
    Call<Void> deletePlans(@Query("ids") List<Long> ids);

    @GET("api/v1/classplans/archived")
    Call<List<ClassPlanDTO.Get>> getArchivedPlans();

    @PUT("api/v1/classplans/archived")
    Call<Void> activatePlans(@Query("ids") List<Long> ids);
}
