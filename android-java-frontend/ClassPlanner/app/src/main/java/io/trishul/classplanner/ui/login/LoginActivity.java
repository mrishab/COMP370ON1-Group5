package io.trishul.classplanner.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import io.trishul.classplanner.R;
import io.trishul.classplanner.network.AuthApi;
import io.trishul.classplanner.network.LoginRequest;
import io.trishul.classplanner.ui.register.RegisterActivity;
import io.trishul.classplanner.MainActivity;
import io.trishul.classplanner.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    public static final String APP_PREFS = "ClassPlannerPrefs";
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private SharedPreferences prefs;

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

        TextView goToRegister = findViewById(R.id.goToRegister);
        goToRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(view -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AuthApi authApi = retrofit.create(AuthApi.class);
            LoginRequest loginRequest = new LoginRequest(email, password);

            authApi.login(loginRequest).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        Toast.makeText(LoginActivity.this, "Welcome " + user.getFirstName(), Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(User.ATTR_NAME_EMAIL, user.getEmail());
                        editor.putString(User.ATTR_NAME_FIRST_NAME, user.getFirstName());
                        editor.putString(User.ATTR_NAME_LAST_NAME, user.getLastName());
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(User.ATTR_NAME_FIRST_NAME, user.getFirstName());
                        intent.putExtra(User.ATTR_NAME_LAST_NAME, user.getLastName());
                        intent.putExtra(User.ATTR_NAME_EMAIL, user.getEmail());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
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