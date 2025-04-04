package io.trishul.classplanner.ui.classplans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.trishul.classplanner.R;
import io.trishul.classplanner.network.ApiClientManager;
import io.trishul.classplanner.network.ClassPlanApi;
import io.trishul.classplanner.network.ClassPlanRequest;
import io.trishul.classplanner.network.ClassPlanResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassPlanFragment extends Fragment {

    private EditText majorInput, yearInput, termsInput;
    private Button generateButton;
    private TextView resultView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_plan, container, false);

        majorInput = view.findViewById(R.id.majorInput);
        yearInput = view.findViewById(R.id.yearInput);
        termsInput = view.findViewById(R.id.termsInput);
        generateButton = view.findViewById(R.id.generatePlanButton);
        resultView = view.findViewById(R.id.classPlanResult);

        generateButton.setOnClickListener(v -> {
            String major = majorInput.getText().toString().trim();
            int year = Integer.parseInt(yearInput.getText().toString().trim());
            int terms = Integer.parseInt(termsInput.getText().toString().trim());

            ClassPlanRequest request = new ClassPlanRequest(major, year, terms);

            ClassPlanApi api = ApiClientManager.getInstance(requireContext()).getClassPlanApi();

            api.generateClassPlan(request).enqueue(new Callback<ClassPlanResponse>() {
                @Override
                public void onResponse(Call<ClassPlanResponse> call, Response<ClassPlanResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ClassPlanResponse plan = response.body();
                        StringBuilder result = new StringBuilder("Plan: " + plan.getPlanName() + "\nCourses:\n");
                        for (String course : plan.getCourses()) {
                            result.append("- ").append(course).append("\n");
                        }
                        resultView.setText(result.toString());
                    } else {
                        Toast.makeText(getContext(), "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ClassPlanResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        return view;
    }
}