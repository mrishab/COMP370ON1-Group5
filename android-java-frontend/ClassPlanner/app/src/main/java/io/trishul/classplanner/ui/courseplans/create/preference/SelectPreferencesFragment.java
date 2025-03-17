package io.trishul.classplanner.ui.courseplans.create.preference;

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

public class SelectPreferencesFragment extends Fragment {
    private FragmentSelectPreferencesBinding binding;
    private SelectPreferencesModal selectPreferencesModal;
    private Button nextButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.selectPreferencesModal =
                new ViewModelProvider(this).get(SelectPreferencesModal.class);

        this.binding = FragmentSelectPreferencesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.nextButton = getActivity().findViewById(R.id.button_create_course_plan_next);

        Slider slider = root.findViewById(R.id.slider_input_desired_number_of_courses);
        TextView textDisplay = root.findViewById(R.id.text_display_desired_number_of_courses);

        slider.addOnChangeListener((s, value, fromUser) -> {
            textDisplay.setText(String.valueOf((int) value));
        });

        activateNextButtonIfReady();

        return root;
    }

    private void activateNextButtonIfReady() {
        nextButton.setEnabled(true);
        nextButton.setOnClickListener(v -> savePreferences());
    }

    private void savePreferences() {
        int desiredCourses = Math.round(binding.sliderInputDesiredNumberOfCourses.getValue());
        selectPreferencesModal.setDesiredNumberOfCourses(desiredCourses);

        int selectedBurdenId = binding.burdenCapacityRadioGroup.getCheckedRadioButtonId();
        if (selectedBurdenId != -1) {
            RadioButton selectedBurden = binding.getRoot().findViewById(selectedBurdenId);
            selectPreferencesModal.setBurdenCapacity(selectedBurden.getText().toString());
        }

        int selectedDistributionId = binding.classDistributionRadioGroup.getCheckedRadioButtonId();
        if (selectedDistributionId != -1) {
            RadioButton selectedDistribution = binding.getRoot().findViewById(selectedDistributionId);
            selectPreferencesModal.setClassDistribution(selectedDistribution.getText().toString());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}