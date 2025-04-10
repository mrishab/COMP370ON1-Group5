package io.trishul.classplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.trishul.classplanner.network.dtos.AvailabilityDTO;
import io.trishul.classplanner.network.dtos.BurdenCapacity;
import io.trishul.classplanner.network.dtos.ClassDistribution;
import io.trishul.classplanner.network.dtos.ClassPlanDTO;
import io.trishul.classplanner.network.dtos.GradPlanDTO;
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

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (currentStep > 0) {
                    onFragmentChange(-1, findViewById(R.id.button_create_class_plan_next));
                } else {
                    showExitConfirmationDialog();
                }
            }
        });

        viewModel = new ViewModelProvider(this).get(CreateNewClassPlanActivityModel.class);

        if (savedInstanceState != null) {
            currentStep = savedInstanceState.getInt(BUNDLE_CURRENT_STEP_KEY, 0);
        }

        fragmentContainer = new CreateNewClassPlanFragmentContainer();
        setAllFragments(); // Add all fragments at start

        Button backButton = findViewById(R.id.button_create_class_plan_back);
        Button nextButton = findViewById(R.id.button_create_class_plan_next);

        setupButtonObservers(nextButton);

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

    private void setupButtonObservers(Button nextButton) {
        viewModel.getSelectedGradPlanId().observe(this, id -> {
            if (currentStep == 0) {
                nextButton.setEnabled(id != null);
            }
        });

        viewModel.getAvailability().observe(this, availability -> {
            if (currentStep == 1) {
                nextButton.setEnabled(viewModel.isMinimumAvailabilitySelected());
            }
        });

        viewModel.getBurdenCapacity().observe(this, burden -> {
            if (currentStep == 2) {
                nextButton.setEnabled(burden != null && viewModel.getClassDistribution().getValue() != null);
            }
        });

        viewModel.getClassDistribution().observe(this, distribution -> {
            if (currentStep == 2) {
                nextButton.setEnabled(distribution != null && viewModel.getBurdenCapacity().getValue() != null);
            }
        });
    }

    private void onFragmentChange(int increment, Button nextButton) {
        int nextStep = getNthStep(increment);
        showFragment(nextStep);
        currentStep = nextStep;
        updateNextButtonText(nextButton);
        nextButton.setEnabled(isCurrentStepValid());
    }

    private boolean isCurrentStepValid() {
        switch (currentStep) {
            case 0:
                return viewModel.getSelectedGradPlanId().getValue() != null;
            case 1:
                return viewModel.isMinimumAvailabilitySelected();
            case 2:
                return viewModel.getBurdenCapacity().getValue() != null && 
                       viewModel.getClassDistribution().getValue() != null;
            default:
                return false;
        }
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
                    try {
                        ClassPlanDTO.Post request = createRequestFromViewModel();
                        Intent intent = new Intent(this, SubmitCreateNewClassPlanActivity.class);
                        intent.putExtra("request", request);
                        startActivity(intent);
                        finish();
                    } catch (IllegalStateException e) {
                        new AlertDialog.Builder(this)
                                .setTitle("Error")
                                .setMessage(e.getMessage())
                                .setPositiveButton("OK", (errorDialog, which2) -> errorDialog.dismiss())
                                .show();
                    }
                })
                .setNegativeButton(R.string.submit_confirmation_negative, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(true)
                .show();
    }

    private ClassPlanDTO.Post createRequestFromViewModel() {
        Long gradPlanId = viewModel.getSelectedGradPlanId().getValue();
        BurdenCapacity burdenCapacity = viewModel.getBurdenCapacity().getValue();
        ClassDistribution distribution = viewModel.getClassDistribution().getValue();
        Map<String, boolean[]> availability = viewModel.getAvailability().getValue();

        if (gradPlanId == null) {
            throw new IllegalStateException("Graduation plan not selected");
        }
        if (burdenCapacity == null) {
            throw new IllegalStateException("Burden capacity not set");
        }
        if (distribution == null) {
            throw new IllegalStateException("Class distribution not set");
        }
        if (availability == null) {
            throw new IllegalStateException("Availability not set");
        }

        ClassPlanDTO.Post request = new ClassPlanDTO.Post();
        request.setGradPlanId(gradPlanId);
        request.setBurdenCapacity(burdenCapacity);
        request.setClassDistribution(distribution);
        request.setAvailability(createAvailabilityDTO(availability));
        return request;
    }

    private AvailabilityDTO createAvailabilityDTO(Map<String, boolean[]> availabilityMap) {
        AvailabilityDTO dto = new AvailabilityDTO();
        List<AvailabilityDTO.AvailabilityDayDTO> days = new ArrayList<>();

        for (Map.Entry<String, boolean[]> entry : availabilityMap.entrySet()) {
            AvailabilityDTO.AvailabilityDayDTO dayDTO = new AvailabilityDTO.AvailabilityDayDTO();
            dayDTO.setDay(entry.getKey());

            List<AvailabilityDTO.AvailabilityHourDTO> hours = new ArrayList<>();
            boolean[] dailyHours = entry.getValue();

            for (int i = 0; i < dailyHours.length; i++) {
                AvailabilityDTO.AvailabilityHourDTO hourDTO = new AvailabilityDTO.AvailabilityHourDTO();
                hourDTO.setHourOfTheDay(i);
                hourDTO.setIsAvailable(dailyHours[i]);
                hours.add(hourDTO);
            }

            dayDTO.setHours(hours);
            days.add(dayDTO);
        }

        dto.setDays(days);
        return dto;
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