package io.trishul.classplanner.network;

import android.content.Context;
import retrofit2.Retrofit;

public class ApiClientManager {
    private static ApiClientManager instance;
    private final Retrofit retrofit;
    
    private AuthApi authApi;
    private ClassPlanApi classPlanApi;
    
    private ApiClientManager(Context context) {
        this.retrofit = ApiConfig.getClient(context);
    }
    
    public static synchronized ApiClientManager getInstance(Context context) {
        if (instance == null) {
            instance = new ApiClientManager(context);
        }
        return instance;
    }
    
    public AuthApi getAuthApi() {
        if (authApi == null) {
            authApi = retrofit.create(AuthApi.class);
        }
        return authApi;
    }
    
    public ClassPlanApi getClassPlanApi() {
        if (classPlanApi == null) {
            classPlanApi = retrofit.create(ClassPlanApi.class);
        }
        return classPlanApi;
    }
}
