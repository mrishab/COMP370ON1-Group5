package io.trishul.classplanner.network;

import android.content.Context;
import android.util.Base64;
import io.trishul.classplanner.utils.SessionManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class AuthInterceptor implements Interceptor {
    private final Context context;
    private final boolean isLoginRequest;

    public AuthInterceptor(Context context, boolean isLoginRequest) {
        this.context = context;
        this.isLoginRequest = isLoginRequest;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        
        if (isLoginRequest) {
            return chain.proceed(original);
        }

        SessionManager sessionManager = new SessionManager(context);
        String credentials = sessionManager.getBasicAuthPlain();
        String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        Request.Builder builder = original.newBuilder()
                .header("Authorization", basic);

        return chain.proceed(builder.build());
    }
}