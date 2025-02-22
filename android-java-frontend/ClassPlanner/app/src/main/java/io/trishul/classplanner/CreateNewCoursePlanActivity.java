package io.trishul.classplanner;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.trishul.classplanner.ui.courseplans.create.CreateNewCoursePlanFragmentContainer;

public class CreateNewCoursePlanActivity extends AppCompatActivity {
    private static final String BUNDLE_CURRENT_STEP_KEY = "currentStep";

    private int currentStep = 0;
    private CreateNewCoursePlanFragmentContainer fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_course_plan);

        if (savedInstanceState != null) {
            currentStep = savedInstanceState.getInt(BUNDLE_CURRENT_STEP_KEY, 0);
        }

        fragmentContainer = new CreateNewCoursePlanFragmentContainer();
        setCurrentFragment();

        Button backButton = findViewById(R.id.button_create_course_plan_back);
        backButton.setOnClickListener(v -> {
            updateCurrentStep(-1);
            setCurrentFragment();
        });

        Button nextButton = findViewById(R.id.button_create_course_plan_next);
        nextButton.setEnabled(false);
        nextButton.setOnClickListener(v -> {
            updateCurrentStep(1);
            setCurrentFragment();
        });
    }

    private int getNthStep(int increment) {
        return Math.min(CreateNewCoursePlanFragmentContainer.MAX_STEPS - 1, Math.max(0, currentStep + increment));
    }

    private void updateCurrentStep(int increment) {
        currentStep = getNthStep(increment);
    }

    private void setCurrentFragment() {
        Fragment fragment = fragmentContainer.getFragment(currentStep);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_create_new_course_plan, fragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_CURRENT_STEP_KEY, currentStep);
    }
}
