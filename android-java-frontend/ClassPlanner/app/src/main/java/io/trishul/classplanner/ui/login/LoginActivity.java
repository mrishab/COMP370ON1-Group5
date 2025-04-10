package io.trishul.classplanner.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.trishul.classplanner.R;
import io.trishul.classplanner.network.LoginApi;
import io.trishul.classplanner.network.dtos.UserDTO;
import io.trishul.classplanner.ui.base.BaseActivity;
import io.trishul.classplanner.ui.register.RegisterActivity;
import io.trishul.classplanner.MainActivity;
import io.trishul.classplanner.constants.EmailConstants;
import io.trishul.classplanner.constants.PasswordConstants;
import io.trishul.classplanner.network.ApiClientManager;
import io.trishul.classplanner.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private TextView emailValidation;
    private boolean isValidEmail = false;
    private boolean isValidPassword = false;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
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
            showToast(getString(R.string.error_fill_all_fields));
            return;
        }

        performLogin(email, password);
    }

    private void performLogin(String email, String password) {
        LoginApi authApi = ApiClientManager.getInstance(this).getLoginApi();
        UserDTO.Login loginRequest = new UserDTO.Login();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        authApi.login(loginRequest).enqueue(new Callback<UserDTO.Get>() {
            @Override
            public void onResponse(Call<UserDTO.Get> call, Response<UserDTO.Get> response) {
                handleLoginResponse(response, password);
            }

            @Override
            public void onFailure(Call<UserDTO.Get> call, Throwable t) {
                t.printStackTrace();
                handleLoginError(t);
            }
        });
    }

    private void handleLoginResponse(Response<UserDTO.Get> response, String password) {
        if (response.isSuccessful()) {
            UserDTO.Get user = response.body();
            handleSuccessfulLogin(user, password);
        } else {
            showToast(getString(R.string.error_login_failed, response.code()));
        }
    }

    private void handleSuccessfulLogin(UserDTO.Get user, String password) {
        showToast(getString(R.string.success_login, user.getFirstName()));
        setLoginSession(user, password);
        navigateToMainActivity(user);
    }

    private void handleLoginError(Throwable t) {
        t.printStackTrace();
        showToast(getString(R.string.error_generic, t.getMessage()));
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToMainActivity(UserDTO.Get user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(UserDTO.ATTR_FIRST_NAME, user.getFirstName());
        intent.putExtra(UserDTO.ATTR_LAST_NAME, user.getLastName());
        intent.putExtra(UserDTO.ATTR_EMAIL, user.getEmail());
        startActivity(intent);
        finish();
    }

    private void setLoginSession(UserDTO.Get user, String password) {
        sessionManager.setLoginSession(user, password);
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}