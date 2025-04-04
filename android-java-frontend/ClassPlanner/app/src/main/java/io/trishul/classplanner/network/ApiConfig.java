package io.trishul.classplanner.network;

import android.content.Context;
import io.trishul.classplanner.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;

public class ApiConfig {
    private static Retrofit retrofit = null;
    
    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            String baseUrl = context.getString(R.string.api_base_url);
            int connectTimeout = Integer.parseInt(context.getString(R.string.api_connect_timeout));
            int readTimeout = Integer.parseInt(context.getString(R.string.api_read_timeout));
            
            OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .build();
                
            retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit;
    }
}
