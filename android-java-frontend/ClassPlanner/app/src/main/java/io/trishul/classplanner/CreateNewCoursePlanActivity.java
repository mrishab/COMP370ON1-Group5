package io.trishul.classplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import io.trishul.classplanner.ui.courseplans.create.CreateNewCoursePlanFragmentContainer;

public class CreateNewCoursePlanActivity extends AppCompatActivity {
    public static final String SHARED_PREFS_KEY = CreateNewCoursePlanActivity.class.getSimpleName();
    public static final String SHARED_PREFS_CURRENT_STEP_KEY = SHARED_PREFS_KEY + ".currentStep";

    private int currentStep;

    private CreateNewCoursePlanFragmentContainer fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_course_plan);

        if (savedInstanceState == null) {
            SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
            currentStep = prefs.getInt(SHARED_PREFS_CURRENT_STEP_KEY, 0);
        }
        fragmentContainer = new CreateNewCoursePlanFragmentContainer(); // TODO: RENAME

        setCurrentFragment();

        Button backButton = findViewById(R.id.button_create_course_plan_back);
        backButton.setOnClickListener(v -> {
            updateCurrentStep(-1);
            setCurrentFragment();
        });

        Button nextButton = findViewById(R.id.button_create_course_plan_next);
        nextButton.setOnClickListener(v -> {
            updateCurrentStep(1);
            setCurrentFragment();
        });
    }

    private void updateCurrentStep(int increment) {
        currentStep = Math.min(CreateNewCoursePlanFragmentContainer.MAX_STEPS - 1, Math.max(0, currentStep + increment));
        getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE)
                .edit()
                .putInt(SHARED_PREFS_CURRENT_STEP_KEY, currentStep)
                .apply();
    }

    private void setCurrentFragment() {
        Fragment fragment = fragmentContainer.getFragment(currentStep);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_create_new_course_plan, fragment)
                .commit();
    }
}