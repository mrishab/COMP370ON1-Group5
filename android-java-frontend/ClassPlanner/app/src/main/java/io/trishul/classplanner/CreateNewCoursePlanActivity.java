package io.trishul.classplanner;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNewCoursePlanActivity extends AppCompatActivity {
    public static final int MAX_STEPS = 4;
    private int currentStep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_course_plan);

        Button backButton = findViewById(R.id.button_create_course_plan_back);
        backButton.setOnClickListener(v -> {
            currentStep = Math.max(0, currentStep - 1);
        });

        Button nextButton = findViewById(R.id.button_create_course_plan_next);
        nextButton.setOnClickListener(v -> {
            currentStep = Math.min(2, currentStep + 1);
        });
    }

}