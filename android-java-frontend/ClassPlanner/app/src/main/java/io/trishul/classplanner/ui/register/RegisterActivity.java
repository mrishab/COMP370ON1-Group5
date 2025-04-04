package io.trishul.classplanner.ui.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import io.trishul.classplanner.network.AuthApi;
import io.trishul.classplanner.network.RegisterRequest;
import io.trishul.classplanner.ui.login.LoginActivity;
import io.trishul.classplanner.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.appcompat.app.AppCompatActivity;
import io.trishul.classplanner.R;
import io.trishul.classplanner.constants.EmailConstants;
import io.trishul.classplanner.constants.PasswordConstants;
import io.trishul.classplanner.network.ApiConfig;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private Button registerButton;
    private TextView passwordLength, passwordUppercase, passwordNumber, passwordSpecial, emailValidation;
    private boolean isValidEmail = false;
    private boolean isValidPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);

        passwordLength = findViewById(R.id.passwordLength);
        passwordUppercase = findViewById(R.id.passwordUppercase);
        passwordNumber = findViewById(R.id.passwordNumber);
        passwordSpecial = findViewById(R.id.passwordSpecial);
        emailValidation = findViewById(R.id.emailValidation);

        registerButton.setEnabled(false);

        setupTextWatchers();

        TextView goToLogin = findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameInput.getText().toString().trim();
                String lastName = lastNameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Retrofit retrofit = ApiConfig.getClient(RegisterActivity.this);
                AuthApi authApi = retrofit.create(AuthApi.class);
                RegisterRequest registerRequest = new RegisterRequest(firstName, lastName, email, password);

                authApi.register(registerRequest).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User user = response.body();
                            Toast.makeText(RegisterActivity.this, "Welcome " + user.getFirstName(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
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
                updateRegisterButton();
            }
        });

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                updatePasswordRequirements(password);
                updateRegisterButton();
            }
        });

        firstNameInput.addTextChangedListener(new SimpleTextWatcher(() -> updateRegisterButton()));
        lastNameInput.addTextChangedListener(new SimpleTextWatcher(() -> updateRegisterButton()));
    }

    private void updatePasswordRequirements(String password) {
        boolean hasLength = password.length() >= PasswordConstants.MIN_LENGTH;
        boolean hasUpper = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(PasswordConstants.NUMBER_PATTERN);
        boolean hasSpecial = password.matches(PasswordConstants.SPECIAL_CHARS_PATTERN);

        updateRequirement(passwordLength, hasLength);
        updateRequirement(passwordUppercase, hasUpper);
        updateRequirement(passwordNumber, hasNumber);
        updateRequirement(passwordSpecial, hasSpecial);

        isValidPassword = hasLength && hasUpper && hasNumber && hasSpecial;
    }

    private void updateRequirement(TextView view, boolean isValid) {
        view.setTextColor(isValid ? 
            getResources().getColor(android.R.color.holo_green_dark) :
            getResources().getColor(android.R.color.holo_red_dark));
    }

    private void updateRegisterButton() {
        boolean isValidName = !firstNameInput.getText().toString().trim().isEmpty() 
            && !lastNameInput.getText().toString().trim().isEmpty();
        registerButton.setEnabled(isValidName && isValidEmail && isValidPassword);
    }

    private class SimpleTextWatcher implements TextWatcher {
        private final Runnable action;

        SimpleTextWatcher(Runnable action) {
            this.action = action;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            action.run();
        }
    }
}