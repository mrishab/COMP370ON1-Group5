package io.trishul.classplanner.network;

import io.trishul.classplanner.api.models.PlanCreationRequest;
import io.trishul.classplanner.api.models.PlanCreationResponse;
import io.trishul.classplanner.api.models.GradPlansRequest;
import io.trishul.classplanner.api.models.GradPlansResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface ClassPlanApi {
    @POST("/api/v1/plans/create")
    Call<PlanCreationResponse> createClassPlan(@Body PlanCreationRequest request);

    @GET("/api/v1/plans")
    Call<GradPlansResponse> getGradPlans();
}