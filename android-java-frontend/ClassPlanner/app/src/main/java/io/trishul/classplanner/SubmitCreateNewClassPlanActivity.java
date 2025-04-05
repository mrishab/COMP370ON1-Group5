package io.trishul.classplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.trishul.classplanner.api.models.PlanCreationRequest;
import io.trishul.classplanner.api.models.PlanCreationResponse;

import java.util.concurrent.CompletableFuture;

import io.trishul.classplanner.network.ApiClientManager;
import io.trishul.classplanner.ui.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitCreateNewClassPlanActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_create_new_class_plan);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        Button retryButton = findViewById(R.id.button_submit_create_class_plan_retry);
        Button viewButton = findViewById(R.id.button_submit_create_class_plan_view);

        // Retrieve the request from the intent
        PlanCreationRequest request = getIntent().getParcelableExtra("request");

        // Function to make API call
        Runnable makeApiCall = () -> {
            retryButton.setEnabled(false);
            viewButton.setEnabled(false);
            progressBar.setVisibility(ProgressBar.VISIBLE);

            ApiClientManager.getInstance(this)
                .getClassPlanApi()
                .createClassPlan(request)
                .enqueue(new Callback<PlanCreationResponse>() {
                    @Override
                    public void onResponse(Call<PlanCreationResponse> call, Response<PlanCreationResponse> response) {
                        progressBar.setVisibility(ProgressBar.GONE);
                        retryButton.setEnabled(true);

                        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                            viewButton.setEnabled(true);
                            viewButton.setOnClickListener(v -> {
                                Intent intent = new Intent(SubmitCreateNewClassPlanActivity.this, MainActivity.class);
                                intent.putExtra("selectedTab", R.id.navigation_grad_plans);
                                intent.putExtra("planId", response.body().getPlanId());
                                startActivity(intent);
                            });
                        } else {
                            String message = response.body() != null ? response.body().getMessage() : "Unknown error occurred";
                            Toast.makeText(SubmitCreateNewClassPlanActivity.this, 
                                getString(R.string.error_create_plan, message), 
                                Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PlanCreationResponse> call, Throwable t) {
                        progressBar.setVisibility(ProgressBar.GONE);
                        retryButton.setEnabled(true);
                        Toast.makeText(SubmitCreateNewClassPlanActivity.this, 
                            getString(R.string.error_create_plan_network, t.getMessage()), 
                            Toast.LENGTH_LONG).show();
                    }
                });
        };

        // Initial API call
        makeApiCall.run();

        // Retry button logic
        retryButton.setOnClickListener(v -> {
            makeApiCall.run();
        });
    }
}