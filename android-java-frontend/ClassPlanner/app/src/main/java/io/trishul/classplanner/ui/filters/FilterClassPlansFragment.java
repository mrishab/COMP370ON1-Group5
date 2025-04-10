package io.trishul.classplanner.ui.filters;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;
import io.trishul.classplanner.R;
import io.trishul.classplanner.ui.classplans.ClassPlanFilterRequest;
import io.trishul.classplanner.ui.classplans.ClassPlansViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterClassPlansFragment extends Fragment {
    private ClassPlansViewModel viewModel;
    private MaterialButton applyButton;
    private ClassPlanFilterRequest lastAppliedFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ClassPlansViewModel.class);
        View root = inflater.inflate(R.layout.fragment_filter_class_plans, container, false);
        
        setupRangeSliders(root);
        applyButton = root.findViewById(R.id.btn_apply_filters);
        setupFilterActions(root);
        setupFilterChangeListeners(root);
        
        return root;
    }
    
    private void setupRangeSliders(View root) {
        // Courses slider
        RangeSlider coursesSlider = root.findViewById(R.id.slider_courses);
        TextView coursesStart = root.findViewById(R.id.tv_courses_start);
        TextView coursesEnd = root.findViewById(R.id.tv_courses_end);
        
        coursesSlider.setValues(1f, 15f);
        coursesSlider.addOnChangeListener((slider, value, fromUser) -> {
            List<Float> values = slider.getValues();
            coursesStart.setText(String.valueOf(Math.round(values.get(0))));
            coursesEnd.setText(String.valueOf(Math.round(values.get(1))));
        });

        // Credits slider
        RangeSlider creditsSlider = root.findViewById(R.id.slider_credits);
        TextView creditsStart = root.findViewById(R.id.tv_credits_start);
        TextView creditsEnd = root.findViewById(R.id.tv_credits_end);
        
        creditsSlider.setValues(1f, 30f);
        creditsSlider.addOnChangeListener((slider, value, fromUser) -> {
            List<Float> values = slider.getValues();
            creditsStart.setText(String.valueOf(Math.round(values.get(0))));
            creditsEnd.setText(String.valueOf(Math.round(values.get(1))));
        });

        // Year slider
        RangeSlider yearSlider = root.findViewById(R.id.slider_year);
        TextView yearStart = root.findViewById(R.id.tv_year_start);
        TextView yearEnd = root.findViewById(R.id.tv_year_end);
        
        yearSlider.setValues(2000f, 2024f);
        yearSlider.addOnChangeListener((slider, value, fromUser) -> {
            List<Float> values = slider.getValues();
            yearStart.setText(String.valueOf(Math.round(values.get(0))));
            yearEnd.setText(String.valueOf(Math.round(values.get(1))));
        });
    }
    
    private void setupFilterActions(View root) {
        root.findViewById(R.id.btn_apply_filters).setOnClickListener(v -> applyFilters());
        root.findViewById(R.id.btn_clear_filters).setOnClickListener(v -> clearFilters());
    }
    
    private void applyFilters() {
        ClassPlanFilterRequest filter = new ClassPlanFilterRequest();
        
        // Get grad plan IDs
        TextInputEditText gradPlanIdsInput = requireView().findViewById(R.id.et_grad_plan_ids);
        String gradPlanIdsText = gradPlanIdsInput.getText().toString();
        if (!gradPlanIdsText.isEmpty()) {
            List<Integer> gradPlanIds = Arrays.stream(gradPlanIdsText.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
            filter.setGradPlanIds(gradPlanIds);
        }
        
        // Get program name and description
        TextInputEditText programNameInput = requireView().findViewById(R.id.et_program_name);
        filter.setProgramName(programNameInput.getText().toString());
        
        TextInputEditText descriptionInput = requireView().findViewById(R.id.et_description);
        filter.setDescription(descriptionInput.getText().toString());
        
        // Get slider values
        RangeSlider coursesSlider = requireView().findViewById(R.id.slider_courses);
        filter.setMinCourses(Math.round(coursesSlider.getValues().get(0)));
        filter.setMaxCourses(Math.round(coursesSlider.getValues().get(1)));
        
        RangeSlider creditsSlider = requireView().findViewById(R.id.slider_credits);
        filter.setMinCredits(Math.round(creditsSlider.getValues().get(0)));
        filter.setMaxCredits(Math.round(creditsSlider.getValues().get(1)));
        
        RangeSlider yearSlider = requireView().findViewById(R.id.slider_year);
        filter.setYearStart(Math.round(yearSlider.getValues().get(0)));
        filter.setYearEnd(Math.round(yearSlider.getValues().get(1)));
        
        // Get selected terms
        ChipGroup termGroup = requireView().findViewById(R.id.chip_group_term);
        List<String> selectedTerms = new ArrayList<>();
        for (int id : termGroup.getCheckedChipIds()) {
            Chip chip = termGroup.findViewById(id);
            selectedTerms.add(chip.getText().toString().toUpperCase());
        }
        filter.setTerms(selectedTerms);
        
        // Get burden capacity
        ChipGroup burdenGroup = requireView().findViewById(R.id.chip_group_burden);
        List<String> selectedBurden = new ArrayList<>();
        for (int id : burdenGroup.getCheckedChipIds()) {
            Chip chip = burdenGroup.findViewById(id);
            selectedBurden.add(chip.getText().toString().toUpperCase());
        }
        filter.setBurdenCapacity(selectedBurden);
        
        // Get class distribution
        ChipGroup distributionGroup = requireView().findViewById(R.id.chip_group_distribution);
        List<String> selectedDistribution = new ArrayList<>();
        for (int id : distributionGroup.getCheckedChipIds()) {
            Chip chip = distributionGroup.findViewById(id);
            selectedDistribution.add(chip.getText().toString().toUpperCase());
        }
        filter.setClassDistribution(selectedDistribution);
        
        // Save and apply filter
        lastAppliedFilter = filter;
        viewModel.setCurrentFilter(filter);
        viewModel.setFiltersApplied(true);
        applyButton.setEnabled(false);
    }

    private void clearFilters() {
        // Reset all UI components to default values
        TextInputEditText gradPlanIdsInput = requireView().findViewById(R.id.et_grad_plan_ids);
        gradPlanIdsInput.setText("");
        
        TextInputEditText programNameInput = requireView().findViewById(R.id.et_program_name);
        programNameInput.setText("");
        
        TextInputEditText descriptionInput = requireView().findViewById(R.id.et_description);
        descriptionInput.setText("");
        
        RangeSlider coursesSlider = requireView().findViewById(R.id.slider_courses);
        coursesSlider.setValues(1f, 15f);
        
        RangeSlider creditsSlider = requireView().findViewById(R.id.slider_credits);
        creditsSlider.setValues(1f, 30f);
        
        RangeSlider yearSlider = requireView().findViewById(R.id.slider_year);
        yearSlider.setValues(2000f, 2024f);
        
        ChipGroup termGroup = requireView().findViewById(R.id.chip_group_term);
        termGroup.clearCheck();
        
        ChipGroup burdenGroup = requireView().findViewById(R.id.chip_group_burden);
        burdenGroup.clearCheck();
        
        ChipGroup distributionGroup = requireView().findViewById(R.id.chip_group_distribution);
        distributionGroup.clearCheck();
        
        // Clear the filter in ViewModel
        viewModel.setCurrentFilter(new ClassPlanFilterRequest());
        viewModel.setFiltersApplied(false);
        lastAppliedFilter = null;
        applyButton.setEnabled(true);
    }

    private void setupFilterChangeListeners(View root) {
        // Add change listeners to all components
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (lastAppliedFilter != null) {
                    applyButton.setEnabled(true);
                }
            }
        };

        TextInputEditText gradPlanIdsInput = root.findViewById(R.id.et_grad_plan_ids);
        gradPlanIdsInput.addTextChangedListener(textWatcher);
        
        TextInputEditText programNameInput = root.findViewById(R.id.et_program_name);
        programNameInput.addTextChangedListener(textWatcher);
        
        TextInputEditText descriptionInput = root.findViewById(R.id.et_description);
        descriptionInput.addTextChangedListener(textWatcher);
        
        RangeSlider coursesSlider = root.findViewById(R.id.slider_courses);
        coursesSlider.addOnChangeListener((slider, value, fromUser) -> {
            if (lastAppliedFilter != null) {
                applyButton.setEnabled(true);
            }
        });

        RangeSlider creditsSlider = root.findViewById(R.id.slider_credits);
        creditsSlider.addOnChangeListener((slider, value, fromUser) -> {
            if (lastAppliedFilter != null) {
                applyButton.setEnabled(true);
            }
        });

        RangeSlider yearSlider = root.findViewById(R.id.slider_year);
        yearSlider.addOnChangeListener((slider, value, fromUser) -> {
            if (lastAppliedFilter != null) {
                applyButton.setEnabled(true);
            }
        });

        ChipGroup termGroup = root.findViewById(R.id.chip_group_term);
        termGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (lastAppliedFilter != null) {
                applyButton.setEnabled(true);
            }
        });

        ChipGroup burdenGroup = root.findViewById(R.id.chip_group_burden);
        burdenGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (lastAppliedFilter != null) {
                applyButton.setEnabled(true);
            }
        });

        ChipGroup distributionGroup = root.findViewById(R.id.chip_group_distribution);
        distributionGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (lastAppliedFilter != null) {
                applyButton.setEnabled(true);
            }
        });
    }
}
