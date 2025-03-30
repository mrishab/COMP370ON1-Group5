package io.trishul.classplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.trishul.classplanner.api.BackendClient;
import io.trishul.classplanner.api.models.PlanCreationRequest;
import io.trishul.classplanner.api.models.PlanCreationResponse;

import java.util.concurrent.CompletableFuture;

public class SubmitCreateNewClassPlanActivity extends AppCompatActivity {
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

            BackendClient.getInstance().createClassPlan(request).thenAccept(response -> {
                progressBar.setVisibility(ProgressBar.GONE);
                retryButton.setEnabled(true);

                if (response.isSuccess()) {
                    viewButton.setEnabled(true);
                    viewButton.setOnClickListener(v -> {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("selectedTab", R.id.navigation_class_plans);
                        intent.putExtra("planId", response.getPlanId());
                        startActivity(intent);
                    });
                } else {
                    Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).exceptionally(e -> {
                progressBar.setVisibility(ProgressBar.GONE);
                retryButton.setEnabled(true);
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return null;
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