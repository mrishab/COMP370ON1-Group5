package io.trishul.classplanner.network;

import android.content.Context;
import retrofit2.Retrofit;
import io.trishul.classplanner.network.api.UserApi;

public class ApiClientManager {
    private static ApiClientManager instance;
    private final Retrofit authenticatedRetrofit;
    private final Retrofit loginRetrofit;
    
    private ClassPlanApi classPlanApi;
    private UserApi userApi;
    private LoginApi loginApi;
    private GradPlanApi gradPlanApi;
    
    private ApiClientManager(Context context) {
        this.loginRetrofit = ApiConfig.getClient(context, true);
        this.authenticatedRetrofit = ApiConfig.getClient(context, false);
    }
    
    public static synchronized ApiClientManager getInstance(Context context) {
        if (instance == null) {
            instance = new ApiClientManager(context);
        }
        return instance;
    }

    public LoginApi getLoginApi() {
        if (loginApi == null) {
            loginApi = loginRetrofit.create(LoginApi.class);
        }
        return loginApi;
    }

    public UserApi getUserApi() {
        if (userApi == null) {
            userApi = authenticatedRetrofit.create(UserApi.class);
        }
        return userApi;
    }

    public GradPlanApi getGradPlanApi() {
        if (gradPlanApi == null) {
            gradPlanApi = authenticatedRetrofit.create(GradPlanApi.class);
        }
        return gradPlanApi;
    }

    public ClassPlanApi getClassPlanApi() {
        if (classPlanApi == null) {
            classPlanApi = authenticatedRetrofit.create(ClassPlanApi.class);
        }
        return classPlanApi;
    }
}
