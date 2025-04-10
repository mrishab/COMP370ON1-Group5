package io.trishul.classplanner.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import io.trishul.classplanner.network.dtos.UserDTO;
import io.trishul.classplanner.ui.login.LoginActivity;

public class SessionManager {
    private static final String APP_PREFS = "ClassPlannerPrefs";
    private final SharedPreferences prefs;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    public void setLoginSession(UserDTO.Get user, String password) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(UserDTO.ATTR_ID, String.valueOf(user.getId()));
        editor.putString(UserDTO.ATTR_EMAIL, user.getEmail());
        editor.putString(UserDTO.ATTR_FIRST_NAME, user.getFirstName());
        editor.putString(UserDTO.ATTR_LAST_NAME, user.getLastName());
        editor.putString(UserDTO.ATTR_PASSWORD, password);
        editor.apply();
    }

    public boolean isLoggedIn() {
        String email = prefs.getString(UserDTO.ATTR_EMAIL, null);
        String password = prefs.getString(UserDTO.ATTR_PASSWORD, null);
        return (email != null && password != null);
    }

    public void clearSession() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    public String getBasicAuthPlain() {
        String email = prefs.getString(UserDTO.ATTR_EMAIL, null);
        String password = prefs.getString(UserDTO.ATTR_PASSWORD, null);
        if (isLoggedIn()) {
            return email + ":" + password;
        }
        clearSession();
        throw new IllegalStateException("User is not logged in");
    }

    public String getUserInfo(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public void enforceLogin() {
        if (!isLoggedIn()) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }
}
