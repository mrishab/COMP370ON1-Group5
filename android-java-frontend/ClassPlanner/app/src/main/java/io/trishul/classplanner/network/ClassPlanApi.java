package io.trishul.classplanner.network;

import io.trishul.classplanner.api.models.PlanCreationRequest;
import io.trishul.classplanner.api.models.PlanCreationResponse;
import io.trishul.classplanner.api.models.GradPlansResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClassPlanApi {
    @POST("/api/v1/plans/create")
    Call<PlanCreationResponse> createClassPlan(@Body PlanCreationRequest request);

    @GET("/api/v1/grad-plans")
    Call<GradPlansResponse> getGradPlans(
        @Query("minCreditsRequired") Integer minCreditsRequired,
        @Query("maxCreditsRequired") Integer maxCreditsRequired,
        @Query("minCreditsCompleted") Integer minCreditsCompleted,
        @Query("maxCreditsCompleted") Integer maxCreditsCompleted,
        @Query("minCGPA") Double minCGPA,
        @Query("maxCGPA") Double maxCGPA,
        @Query("levels") String levels,
        @Query("degree") String degree,
        @Query("major") String major,
        @Query("terms") String terms,
        @Query("yearStart") Integer yearStart,
        @Query("yearEnd") Integer yearEnd
    );

    @GET("/api/v1/class-plans")
    Call<ClassPlansResponse> getClassPlans(
        @Query("gradPlanIds") String gradPlanIds,
        @Query("programName") String programName,
        @Query("description") String description,
        @Query("minCourses") Integer minCourses,
        @Query("maxCourses") Integer maxCourses,
        @Query("minCredits") Integer minCredits,
        @Query("maxCredits") Integer maxCredits,
        @Query("terms") String terms,
        @Query("yearStart") Integer yearStart,
        @Query("yearEnd") Integer yearEnd,
        @Query("burdenCapacity") String burdenCapacity,
        @Query("classDistribution") String classDistribution
    );
}