package io.trishul.classplanner.ui.filters;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import io.trishul.classplanner.R;
import io.trishul.classplanner.api.models.GradPlanFilterRequest;
import io.trishul.classplanner.ui.gradplans.GradPlansViewModel;

import java.util.ArrayList;
import java.util.List;

public class FilterGradPlansFragment extends Fragment {
    private io.trishul.classplanner.ui.gradplans.GradPlansViewModel viewModel;
    private MaterialButton applyButton;
    private GradPlanFilterRequest lastAppliedFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(GradPlansViewModel.class);
        View root = inflater.inflate(R.layout.fragment_filter_grad_plans, container, false);
        
        setupRangeSliders(root);

        // Setup degree autocomplete
        MaterialAutoCompleteTextView degreeInput = root.findViewById(R.id.et_degree);
        String[] degrees = getResources().getStringArray(R.array.degree_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            degrees
        );
        degreeInput.setAdapter(adapter);
        
        applyButton = root.findViewById(R.id.btn_apply_filters);
        setupFilterActions(root);
        
        // Listen for value changes to enable/disable apply button
        setupFilterChangeListeners(root);
        
        return root;
    }
    
    private void setupRangeSliders(View root) {
        // Credits Required slider
        RangeSlider creditsRequiredSlider = root.findViewById(R.id.slider_credits_required);
        TextView creditsRequiredStart = root.findViewById(R.id.tv_credits_required_start);
        TextView creditsRequiredEnd = root.findViewById(R.id.tv_credits_required_end);
        
        creditsRequiredSlider.setValues(0f, 120f);
        creditsRequiredSlider.addOnChangeListener((slider, value, fromUser) -> {
            List<Float> values = slider.getValues();
            creditsRequiredStart.setText(String.valueOf(Math.round(values.get(0))));
            creditsRequiredEnd.setText(String.valueOf(Math.round(values.get(1))));
        });

        // Credits Completed slider
        RangeSlider creditsCompletedSlider = root.findViewById(R.id.slider_credits_completed);
        TextView creditsCompletedStart = root.findViewById(R.id.tv_credits_completed_start);
        TextView creditsCompletedEnd = root.findViewById(R.id.tv_credits_completed_end);
        
        creditsCompletedSlider.setValues(0f, 120f);
        creditsCompletedSlider.addOnChangeListener((slider, value, fromUser) -> {
            List<Float> values = slider.getValues();
            creditsCompletedStart.setText(String.valueOf(Math.round(values.get(0))));
            creditsCompletedEnd.setText(String.valueOf(Math.round(values.get(1))));
        });

        // CGPA slider
        RangeSlider cgpaSlider = root.findViewById(R.id.slider_cgpa);
        TextView cgpaStart = root.findViewById(R.id.tv_cgpa_start);
        TextView cgpaEnd = root.findViewById(R.id.tv_cgpa_end);
        
        cgpaSlider.setValues(0f, 4.33f);
        cgpaSlider.addOnChangeListener((slider, value, fromUser) -> {
            List<Float> values = slider.getValues();
            cgpaStart.setText(String.format("%.2f", values.get(0)));
            cgpaEnd.setText(String.format("%.2f", values.get(1)));
        });

        // Year slider
        RangeSlider yearSlider = root.findViewById(R.id.slider_year);
        TextView yearStart = root.findViewById(R.id.tv_year_start);
        TextView yearEnd = root.findViewById(R.id.tv_year_end);
        
        yearSlider.setValues(2020f, 2030f);
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
        GradPlanFilterRequest filter = new GradPlanFilterRequest();
        
        // Get values from UI components and set them in filter
        RangeSlider creditsRequiredSlider = requireView().findViewById(R.id.slider_credits_required);
        filter.setMinCreditsRequired(Math.round(creditsRequiredSlider.getValues().get(0)));
        filter.setMaxCreditsRequired(Math.round(creditsRequiredSlider.getValues().get(1)));
        
        RangeSlider creditsCompletedSlider = requireView().findViewById(R.id.slider_credits_completed);
        filter.setMinCreditsCompleted(Math.round(creditsCompletedSlider.getValues().get(0)));
        filter.setMaxCreditsCompleted(Math.round(creditsCompletedSlider.getValues().get(1)));
        
        RangeSlider cgpaSlider = requireView().findViewById(R.id.slider_cgpa);
        filter.setMinCGPA(cgpaSlider.getValues().get(0));
        filter.setMaxCGPA(cgpaSlider.getValues().get(1));
        
        RangeSlider yearSlider = requireView().findViewById(R.id.slider_year);
        filter.setYearStart(Math.round(yearSlider.getValues().get(0)));
        filter.setYearEnd(Math.round(yearSlider.getValues().get(1)));
        
        // Get selected chips
        ChipGroup levelGroup = requireView().findViewById(R.id.chip_group_level);
        List<String> selectedLevels = new ArrayList<>();
        for (int id : levelGroup.getCheckedChipIds()) {
            Chip chip = levelGroup.findViewById(id);
            selectedLevels.add(chip.getText().toString());
        }
        filter.setLevels(selectedLevels);
        
        // Get degree and major
        MaterialAutoCompleteTextView degreeInput = requireView().findViewById(R.id.et_degree);
        filter.setDegree(degreeInput.getText().toString());
        
        TextInputEditText majorInput = requireView().findViewById(R.id.et_major);
        filter.setMajor(majorInput.getText().toString());
        
        // Save and apply filter
        lastAppliedFilter = filter;
        viewModel.setCurrentFilter(filter);
        viewModel.setFiltersApplied(true);
        applyButton.setEnabled(false);
    }

    private void clearFilters() {
        // Reset all UI components to default values
        RangeSlider creditsRequiredSlider = requireView().findViewById(R.id.slider_credits_required);
        creditsRequiredSlider.setValues(0f, 120f);
        
        RangeSlider creditsCompletedSlider = requireView().findViewById(R.id.slider_credits_completed);
        creditsCompletedSlider.setValues(0f, 120f);
        
        RangeSlider cgpaSlider = requireView().findViewById(R.id.slider_cgpa);
        cgpaSlider.setValues(0f, 4.33f);
        
        RangeSlider yearSlider = requireView().findViewById(R.id.slider_year);
        yearSlider.setValues(2020f, 2030f);
        
        ChipGroup levelGroup = requireView().findViewById(R.id.chip_group_level);
        levelGroup.clearCheck();
        
        MaterialAutoCompleteTextView degreeInput = requireView().findViewById(R.id.et_degree);
        degreeInput.setText("");
        
        TextInputEditText majorInput = requireView().findViewById(R.id.et_major);
        majorInput.setText("");
        
        // Clear the filter in ViewModel
        viewModel.setCurrentFilter(new GradPlanFilterRequest());
        viewModel.setFiltersApplied(false);
        lastAppliedFilter = null;
        applyButton.setEnabled(true);
    }
    
    private void setupFilterChangeListeners(View root) {
        // Add listeners to all filter components that enable the apply button when values change
        RangeSlider creditsRequiredSlider = root.findViewById(R.id.slider_credits_required);
        creditsRequiredSlider.addOnChangeListener((slider, value, fromUser) -> {
            if (lastAppliedFilter != null) {
                applyButton.setEnabled(true);
            }
        });
        
        RangeSlider creditsCompletedSlider = root.findViewById(R.id.slider_credits_completed);
        creditsCompletedSlider.addOnChangeListener((slider, value, fromUser) -> {
            if (lastAppliedFilter != null) {
                applyButton.setEnabled(true);
            }
        });
        
        RangeSlider cgpaSlider = root.findViewById(R.id.slider_cgpa);
        cgpaSlider.addOnChangeListener((slider, value, fromUser) -> {
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
        
        ChipGroup levelGroup = root.findViewById(R.id.chip_group_level);
        levelGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (lastAppliedFilter != null) {
                applyButton.setEnabled(true);
            }
        });
        
        MaterialAutoCompleteTextView degreeInput = root.findViewById(R.id.et_degree);
        degreeInput.setOnItemClickListener((parent, view, position, id) -> {
            if (lastAppliedFilter != null) {
                applyButton.setEnabled(true);
            }
        });
        
        TextInputEditText majorInput = root.findViewById(R.id.et_major);
        majorInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lastAppliedFilter != null) {
                    applyButton.setEnabled(true);
                }
            }
        });
    }
}
