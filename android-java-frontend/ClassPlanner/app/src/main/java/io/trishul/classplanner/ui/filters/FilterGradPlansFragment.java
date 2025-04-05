package io.trishul.classplanner.ui.filters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import io.trishul.classplanner.R;

import java.util.List;

public class FilterGradPlansFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        
        // Setup filter actions
        setupFilterActions(root);
        
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
        // TODO: Implement filter application logic
    }
    
    private void clearFilters() {
        // TODO: Implement filter clearing logic
    }
}
