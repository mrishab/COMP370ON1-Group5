package io.trishul.classplanner.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import io.trishul.classplanner.model.User;
import io.trishul.classplanner.ui.login.LoginActivity;

public class SessionManager {
    private static final String APP_PREFS = "ClassPlannerPrefs";
    private final SharedPreferences prefs;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    public void setLoginSession(User user) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(User.ATTR_NAME_EMAIL, user.getEmail());
        editor.putString(User.ATTR_NAME_FIRST_NAME, user.getFirstName());
        editor.putString(User.ATTR_NAME_LAST_NAME, user.getLastName());
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getString(User.ATTR_NAME_EMAIL, null) != null;
    }

    public void clearSession() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
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
