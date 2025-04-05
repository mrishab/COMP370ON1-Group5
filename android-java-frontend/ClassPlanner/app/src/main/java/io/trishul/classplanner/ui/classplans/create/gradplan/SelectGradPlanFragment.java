package io.trishul.classplanner.ui.classplans.create.gradplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.trishul.classplanner.R;
import io.trishul.classplanner.databinding.FragmentSelectGradPlanBinding;
import io.trishul.classplanner.api.models.GradPlansResponse;
import io.trishul.classplanner.network.ApiClientManager;
import io.trishul.classplanner.ui.classplans.create.CreateNewClassPlanActivityModel;
import io.trishul.classplanner.ui.gradplans.GradPlansAdapter;
import io.trishul.classplanner.ui.gradplans.GradPlansViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectGradPlanFragment extends Fragment {
    private FragmentSelectGradPlanBinding binding;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CreateNewClassPlanActivityModel createNewClassPlanActivityModel;
    private Button nextButton;
    private GradPlansViewModel gradPlansViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
        this.createNewClassPlanActivityModel = 
            new ViewModelProvider(requireActivity()).get(CreateNewClassPlanActivityModel.class);
        this.gradPlansViewModel = 
            new ViewModelProvider(requireActivity()).get(GradPlansViewModel.class);
        this.nextButton = getActivity().findViewById(R.id.button_create_class_plan_next);

        binding = FragmentSelectGradPlanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerViewGradPlans;
        swipeRefreshLayout = binding.swipeRefreshLayout;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this::fetchGradPlans);

        createNewClassPlanActivityModel.getSelectedGradPlanId().observe(
            getViewLifecycleOwner(), 
            gradPlanId -> nextButton.setEnabled(gradPlanId != null)
        );

        fetchGradPlans();
        return root;
    }

    private void fetchGradPlans() {
        // Reuse existing grad plans fetching logic
        recyclerView.setVisibility(View.GONE);
        ApiClientManager.getInstance(requireContext())
            .getClassPlanApi()
            .getGradPlans(null, null, null, null, null, null, null, null, null, null, null, null)
            .enqueue(new Callback<GradPlansResponse>() {
                @Override
                public void onResponse(Call<GradPlansResponse> call, Response<GradPlansResponse> response) {
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    
                    if (response.isSuccessful() && response.body() != null) {
                        GradPlansAdapter adapter = new SelectableGradPlansAdapter(
                            response.body().getGradPlans(),
                            createNewClassPlanActivityModel
                        );
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(requireContext(),
                            getString(R.string.error_load_grad_plans, response.code()),
                            Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GradPlansResponse> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(requireContext(),
                        getString(R.string.error_load_grad_plans_network, t.getMessage()),
                        Toast.LENGTH_LONG).show();
                }
            });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
