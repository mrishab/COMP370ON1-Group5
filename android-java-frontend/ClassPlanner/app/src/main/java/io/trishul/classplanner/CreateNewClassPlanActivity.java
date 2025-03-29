package io.trishul.classplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import io.trishul.classplanner.ui.classplans.create.CreateNewClassPlanFragmentContainer;

public class CreateNewClassPlanActivity extends AppCompatActivity {
    private static final String BUNDLE_CURRENT_STEP_KEY = "currentStep";

    private int currentStep = 0;
    private CreateNewClassPlanFragmentContainer fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_class_plan);

        if (savedInstanceState != null) {
            currentStep = savedInstanceState.getInt(BUNDLE_CURRENT_STEP_KEY, 0);
        }

        fragmentContainer = new CreateNewClassPlanFragmentContainer();
        setCurrentFragment();

        Button backButton = findViewById(R.id.button_create_class_plan_back);
        backButton.setOnClickListener(v -> {
            if (currentStep > 0) {
                // If not on the first step, just go back to previous step
                updateCurrentStep(-1);
                setCurrentFragment();
            } else {
                // If on the first step, show exit confirmation dialog
                showExitConfirmationDialog();
            }
        });

        Button nextButton = findViewById(R.id.button_create_class_plan_next);
        nextButton.setEnabled(false);
        nextButton.setOnClickListener(v -> {
            updateCurrentStep(1);
            setCurrentFragment();
        });
    }

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.exit_confirmation_title)
                .setMessage(R.string.exit_confirmation_message)
                .setPositiveButton(R.string.exit_confirmation_positive, (dialog, which) -> {
                    // Navigate back to MainActivity
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.exit_confirmation_negative, (dialog, which) -> {
                    // Dismiss the dialog (does nothing else)
                    dialog.dismiss();
                })
                .setCancelable(true)
                .show();
    }

    @Override
    public void onBackPressed() {
        if (currentStep > 0) {
            // If not on the first step, just go back to previous step
            updateCurrentStep(-1);
            setCurrentFragment();
        } else {
            // If on the first step, show exit confirmation dialog
            showExitConfirmationDialog();
        }
    }

    private int getNthStep(int increment) {
        return Math.min(CreateNewClassPlanFragmentContainer.MAX_STEPS - 1, Math.max(0, currentStep + increment));
    }

    private void updateCurrentStep(int increment) {
        currentStep = getNthStep(increment);
    }

    private void setCurrentFragment() {
        Fragment fragment = fragmentContainer.getFragment(currentStep);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_create_new_class_plan, fragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_CURRENT_STEP_KEY, currentStep);
    }
}
