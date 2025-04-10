package io.trishul.classplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import io.trishul.classplanner.ui.base.BaseActivity;
import io.trishul.classplanner.ui.classplans.create.CreateNewClassPlanActivityModel;
import io.trishul.classplanner.ui.classplans.create.CreateNewClassPlanFragmentContainer;

public class CreateNewClassPlanActivity extends BaseActivity {
    private static final String BUNDLE_CURRENT_STEP_KEY = "currentStep";

    private int currentStep = 0;
    private CreateNewClassPlanFragmentContainer fragmentContainer;
    private CreateNewClassPlanActivityModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_class_plan);

        viewModel = new ViewModelProvider(this).get(CreateNewClassPlanActivityModel.class);

        if (savedInstanceState != null) {
            currentStep = savedInstanceState.getInt(BUNDLE_CURRENT_STEP_KEY, 0);
        }

        fragmentContainer = new CreateNewClassPlanFragmentContainer();
        setAllFragments(); // Add all fragments at start

        Button backButton = findViewById(R.id.button_create_class_plan_back);
        Button nextButton = findViewById(R.id.button_create_class_plan_next);

        backButton.setOnClickListener(v -> {
            if (currentStep > 0) {
                onFragmentChange(-1, nextButton);
            } else {
                showExitConfirmationDialog();
            }
        });

        nextButton.setOnClickListener(v -> {
            if (CreateNewClassPlanFragmentContainer.isLastStep(currentStep)) {
                showSubmitConfirmationDialog();
            } else {
                onFragmentChange(1, nextButton);
            }
        });
    }

    private void onFragmentChange(int increment, Button nextButton) {
        int nextStep = getNthStep(increment);
        showFragment(nextStep);
        currentStep = nextStep;
        updateNextButtonText(nextButton);
    }

    private void updateNextButtonText(Button nextButton) {
        nextButton.setText(CreateNewClassPlanFragmentContainer.isLastStep(currentStep) 
            ? R.string.button_text_submit 
            : R.string.button_text_create_new_class_plan_next_button);
    }

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.exit_confirmation_title)
                .setMessage(R.string.exit_confirmation_message)
                .setPositiveButton(R.string.exit_confirmation_positive, (dialog, which) -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.exit_confirmation_negative, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(true)
                .show();
    }

    private void showSubmitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.submit_confirmation_title)
                .setMessage(R.string.submit_confirmation_message)
                .setPositiveButton(R.string.submit_confirmation_positive, (dialog, which) -> {
                    // Create request and start the submit activity
                    PlanCreationRequest request = createRequestFromViewModel();
                    Intent intent = new Intent(this, SubmitCreateNewClassPlanActivity.class);
                    intent.putExtra("request", request);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.submit_confirmation_negative, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(true)
                .show();
    }

    private PlanCreationRequest createRequestFromViewModel() {
        PlanCreationRequest request = new PlanCreationRequest();
        request.setGradPlanId(viewModel.getSelectedGradPlanId().getValue());
        request.setDesiredNumberOfClasses(viewModel.getDesiredNumberOfClasses().getValue());
        request.setBurdenCapacity(viewModel.getBurdenCapacity().getValue());
        request.setClassDistribution(viewModel.getClassDistribution().getValue());
        request.setAvailability(viewModel.getAvailability().getValue());
        return request;
    }

    @Override
    public void onBackPressed() {
        if (currentStep > 0) {
            onFragmentChange(-1, findViewById(R.id.button_create_class_plan_next));
        } else {
            showExitConfirmationDialog();
        }
    }

    private int getNthStep(int increment) {
        return Math.min(CreateNewClassPlanFragmentContainer.MAX_STEPS - 1, 
                        Math.max(0, currentStep + increment));
    }

    /**
     * **Step 1: Add all fragments initially and hide them**
     */
    private void setAllFragments() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        for (int i = 0; i < CreateNewClassPlanFragmentContainer.MAX_STEPS; i++) {
            Fragment fragment = fragmentContainer.getFragment(i);
            transaction.add(R.id.fragment_container_create_new_class_plan, fragment, "Step" + i);
            if (i != currentStep) {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
    }

    /**
     * **Step 2: Show the required fragment and hide the others**
     */
    private void showFragment(int step) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        for (int i = 0; i < CreateNewClassPlanFragmentContainer.MAX_STEPS; i++) {
            Fragment fragment = fm.findFragmentByTag("Step" + i);
            if (fragment != null) {
                if (i == step) {
                    transaction.show(fragment);
                } else {
                    transaction.hide(fragment);
                }
            }
        }
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_CURRENT_STEP_KEY, currentStep);
    }
}