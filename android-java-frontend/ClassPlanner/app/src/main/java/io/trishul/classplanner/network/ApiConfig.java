package io.trishul.classplanner.network;

import android.content.Context;
import io.trishul.classplanner.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;

public class ApiConfig {
    private static Retrofit authenticatedRetrofit = null;
    private static Retrofit loginRetrofit = null;
    
    public static Retrofit getClient(Context context, boolean isLoginRequest) {
        if (isLoginRequest) {
            if (loginRetrofit == null) {
                loginRetrofit = createRetrofit(context, true);
            }
            return loginRetrofit;
        } else {
            if (authenticatedRetrofit == null) {
                authenticatedRetrofit = createRetrofit(context, false);
            }
            return authenticatedRetrofit;
        }
    }
    
    private static Retrofit createRetrofit(Context context, boolean isLoginRequest) {
        String baseUrl = context.getString(R.string.api_base_url);
        int connectTimeout = Integer.parseInt(context.getString(R.string.api_connect_timeout));
        int readTimeout = Integer.parseInt(context.getString(R.string.api_read_timeout));
        
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS);
            
        if (!isLoginRequest) {
            clientBuilder.addInterceptor(new AuthInterceptor(context, false));
        }
                
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
}
