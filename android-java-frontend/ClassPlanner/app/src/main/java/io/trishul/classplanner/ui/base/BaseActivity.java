package io.trishul.classplanner.ui.base;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import io.trishul.classplanner.utils.SessionManager;
import io.trishul.classplanner.ui.login.LoginActivity;
import io.trishul.classplanner.ui.register.RegisterActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        
        // Skip session check for Login and Register activities
        if (!(this instanceof LoginActivity) && !(this instanceof RegisterActivity)) {
            sessionManager.enforceLogin();
        }
    }
}
