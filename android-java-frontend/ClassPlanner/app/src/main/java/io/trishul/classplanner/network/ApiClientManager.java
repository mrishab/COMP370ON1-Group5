package io.trishul.classplanner.network;

import android.content.Context;
import retrofit2.Retrofit;

public class ApiClientManager {
    private static ApiClientManager instance;
    private final Retrofit retrofit;
    
    private ClassPlanApi classPlanApi;
    private UserApi userApi;
    private GradPlanApi gradPlanApi;
    
    private ApiClientManager(Context context) {
        this.retrofit = ApiConfig.getClient(context);
    }
    
    public static synchronized ApiClientManager getInstance(Context context) {
        if (instance == null) {
            instance = new ApiClientManager(context);
        }
        return instance;
    }

    public UserApi getUserApi() {
        if (userApi == null) {
            userApi = retrofit.create(UserApi.class);
        }
        return userApi;
    }

    public GradPlanApi getGradPlanApi() {
        if (gradPlanApi == null) {
            gradPlanApi = retrofit.create(GradPlanApi.class);
        }
        return gradPlanApi;
    }

    public ClassPlanApi getClassPlanApi() {
        if (classPlanApi == null) {
            classPlanApi = retrofit.create(ClassPlanApi.class);
        }
        return classPlanApi;
    }
}
