package io.trishul.classplanner.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import io.trishul.classplanner.R;
import io.trishul.classplanner.network.AuthApi;
import io.trishul.classplanner.network.LoginRequest;
import io.trishul.classplanner.ui.register.RegisterActivity;
import io.trishul.classplanner.MainActivity;
import io.trishul.classplanner.model.User;
import io.trishul.classplanner.constants.EmailConstants;
import io.trishul.classplanner.constants.PasswordConstants;
import io.trishul.classplanner.network.ApiConfig;
import io.trishul.classplanner.network.ApiClientManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    public static final String APP_PREFS = "ClassPlannerPrefs";
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private TextView emailValidation;
    private SharedPreferences prefs;
    private boolean isValidEmail = false;
    private boolean isValidPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        String userEmail = prefs.getString(User.ATTR_NAME_EMAIL, null);

        if (userEmail != null) {
            openMainActivity();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        emailValidation = findViewById(R.id.emailValidation);

        loginButton.setEnabled(false);

        setupTextWatchers();

        TextView goToRegister = findViewById(R.id.goToRegister);
        goToRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(this::handleLoginClick);
    }

    private void setupTextWatchers() {
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString().trim();
                isValidEmail = email.matches(EmailConstants.EMAIL_PATTERN);
                emailValidation.setTextColor(isValidEmail ? 
                    getResources().getColor(android.R.color.holo_green_dark) :
                    getResources().getColor(android.R.color.holo_red_dark));
                updateLoginButton();
            }
        });

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                isValidPassword = s.length() > 0;
                updateLoginButton();
            }
        });
    }

    private void updateLoginButton() {
        loginButton.setEnabled(isValidEmail && isValidPassword);
    }

    private void handleLoginClick(View view) {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showToast("Please fill in all fields");
            return;
        }

        if (isAdminLogin(email, password)) {
            handleAdminLogin();
            return;
        }

        performLoginRequest(email, password);
    }

    private boolean isAdminLogin(String email, String password) {
        return EmailConstants.ADMIN_EMAIL.equals(email) && 
               PasswordConstants.ADMIN_PASSWORD.equals(password);
    }

    private void handleAdminLogin() {
        User adminUser = new User("Admin", "nimda", "admin@student.ufv.ca", "admin");
        handleSuccessfulLogin(adminUser);
    }

    private void performLoginRequest(String email, String password) {
        AuthApi authApi = ApiClientManager.getInstance(this).getAuthApi();
        LoginRequest loginRequest = new LoginRequest(email, password);

        authApi.login(loginRequest).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                handleLoginResponse(response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                handleLoginError(t);
            }
        });
    }

    private void handleLoginResponse(Response<User> response) {
        if (response.isSuccessful()) {
            User user = response.body();
            handleSuccessfulLogin(user);
        } else {
            showToast("Login failed: " + response.code());
        }
    }

    private void handleSuccessfulLogin(User user) {
        showToast("Welcome " + user.getFirstName());
        setLoginSession(user);
        navigateToMainActivity(user);
    }

    private void handleLoginError(Throwable t) {
        showToast("Error: " + t.getMessage());
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToMainActivity(User user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(User.ATTR_NAME_FIRST_NAME, user.getFirstName());
        intent.putExtra(User.ATTR_NAME_LAST_NAME, user.getLastName());
        intent.putExtra(User.ATTR_NAME_EMAIL, user.getEmail());
        startActivity(intent);
        finish();
    }

    private void setLoginSession(User user) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(User.ATTR_NAME_EMAIL, user.getEmail());
        editor.putString(User.ATTR_NAME_FIRST_NAME, user.getFirstName());
        editor.putString(User.ATTR_NAME_LAST_NAME, user.getLastName());
        editor.apply();
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}