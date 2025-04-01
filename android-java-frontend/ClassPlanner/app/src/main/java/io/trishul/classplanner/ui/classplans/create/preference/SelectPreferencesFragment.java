package io.trishul.classplanner.ui.classplans.create.preference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.slider.Slider;

import io.trishul.classplanner.databinding.FragmentSelectPreferencesBinding;
import io.trishul.classplanner.R;
import io.trishul.classplanner.ui.classplans.create.CreateNewClassPlanActivityModel;

public class SelectPreferencesFragment extends Fragment {
    private FragmentSelectPreferencesBinding binding;
    private CreateNewClassPlanActivityModel createNewClassPlanActivityModel;
    private Button nextButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.createNewClassPlanActivityModel =
                new ViewModelProvider(requireActivity()).get(CreateNewClassPlanActivityModel.class);

        this.binding = FragmentSelectPreferencesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.nextButton = getActivity().findViewById(R.id.button_create_class_plan_next);
        activateNextButtonIfReady();

        hydrateOnClickListeners(root);

        return root;
    }

    private void hydrateOnClickListeners(View root) {
        Slider slider = root.findViewById(R.id.slider_input_desired_number_of_classes);
        TextView textDisplay = root.findViewById(R.id.text_display_desired_number_of_classes);

        slider.addOnChangeListener((s, value, fromUser) -> {
            textDisplay.setText(String.valueOf((int) value));
            createNewClassPlanActivityModel.setDesiredNumberOfClasses((int) value);
        });

        
        binding.burdenCapacityRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedBurden = binding.getRoot().findViewById(checkedId);
            createNewClassPlanActivityModel.setBurdenCapacity(selectedBurden.getText().toString());
        });

        binding.classDistributionRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedDistribution = binding.getRoot().findViewById(checkedId);
            createNewClassPlanActivityModel.setClassDistribution(selectedDistribution.getText().toString());
        });
        
        // Setting default values in the model
        int desiredClasses = Math.round(binding.sliderInputDesiredNumberOfClasses.getValue());
        createNewClassPlanActivityModel.setDesiredNumberOfClasses(desiredClasses);
        RadioButton selectedBurden = binding.getRoot().findViewById(binding.burdenCapacityRadioGroup.getCheckedRadioButtonId());
        createNewClassPlanActivityModel.setBurdenCapacity(selectedBurden.getText().toString());
        RadioButton selectedDistribution = binding.getRoot().findViewById(binding.classDistributionRadioGroup.getCheckedRadioButtonId());
        createNewClassPlanActivityModel.setClassDistribution(selectedDistribution.getText().toString());

    }

    @Override
    public void onHiddenChanged(boolean isHidden) {
        super.onHiddenChanged(isHidden);
        activateNextButtonIfReady();
    }

    private void activateNextButtonIfReady() {
        nextButton.setEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}