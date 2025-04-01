package io.trishul.classplanner.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClassPlanApi {
    @POST("/api/v1/classplan")
    Call<ClassPlanResponse> generateClassPlan(@Body ClassPlanRequest request);
}