package io.trishul.classplanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SubmitCreateNewClassPlanActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_create_new_class_plan);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        Button retryButton = findViewById(R.id.button_submit_create_class_plan_retry);
        Button viewButton = findViewById(R.id.button_submit_create_class_plan_view);

        retryButton.setOnClickListener(v -> {
        });

        viewButton.setOnClickListener(v -> {
        });
    }

}