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
import io.trishul.classplanner.api.models.PlanCreationRequest;
import io.trishul.classplanner.api.models.PlanCreationResponse;
import io.trishul.classplanner.api.models.ClassDetail;
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

            PlanCreationRequest request = new PlanCreationRequest();
            request.setGradPlanId(1L); // TODO: Replace with actual grad plan ID
            request.setDesiredNumberOfClasses(terms);
            request.setBurdenCapacity("MEDIUM"); // Default value
            request.setClassDistribution("CONCENTRATED"); // Default value

            ClassPlanApi api = ApiClientManager.getInstance(requireContext()).getClassPlanApi();

            api.createClassPlan(request).enqueue(new Callback<PlanCreationResponse>() {
                @Override
                public void onResponse(Call<PlanCreationResponse> call, Response<PlanCreationResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        PlanCreationResponse plan = response.body();
                        StringBuilder result = new StringBuilder();
                        result.append("Plan ID: ").append(plan.getPlanId()).append("\n\n");
                        
                        if (plan.getClasses() != null) {
                            result.append("Classes:\n");
                            for (ClassDetail classDetail : plan.getClasses()) {
                                result.append("- ").append(classDetail.getCourse())
                                      .append(" ").append(classDetail.getCourseNumber())
                                      .append("\n  ").append(classDetail.getDescription())
                                      .append("\n");
                            }
                        }
                        
                        resultView.setText(result.toString());
                    } else {
                        Toast.makeText(getContext(), "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PlanCreationResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        return view;
    }
}