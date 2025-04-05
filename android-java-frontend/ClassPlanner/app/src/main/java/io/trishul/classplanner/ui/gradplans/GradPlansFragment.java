package io.trishul.classplanner.ui.gradplans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.trishul.classplanner.R;
import io.trishul.classplanner.databinding.FragmentGradPlansBinding;
import io.trishul.classplanner.api.models.GradPlansResponse;
import io.trishul.classplanner.api.models.GradPlanFilterRequest;

import io.trishul.classplanner.network.ApiClientManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradPlansFragment extends Fragment {

    private FragmentGradPlansBinding binding;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GradPlansViewModel gradPlanViewModel;
    private GradPlansViewModel activityViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        gradPlanViewModel = new ViewModelProvider(this).get(GradPlansViewModel.class);
        activityViewModel = new ViewModelProvider(requireActivity()).get(GradPlansViewModel.class);

        binding = FragmentGradPlansBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerViewGradPlans;
        swipeRefreshLayout = binding.swipeRefreshLayout;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this::fetchGradPlans);

        // Observe filter changes from activity ViewModel
        activityViewModel.getCurrentFilter().observe(getViewLifecycleOwner(), filter -> {
            if (activityViewModel.getFiltersApplied().getValue() == Boolean.TRUE) {
                fetchGradPlans();
            }
        });

        fetchGradPlans();
        return root;
    }

    private void fetchGradPlans() {
        recyclerView.setVisibility(View.GONE);
        GradPlanFilterRequest filter = activityViewModel.getCurrentFilter().getValue();

        // Convert lists to comma-separated strings for API
        String levels = filter.getLevels() != null ? String.join(",", filter.getLevels()) : null;
        String terms = filter.getTerms() != null ? String.join(",", filter.getTerms()) : null;

        ApiClientManager.getInstance(requireContext())
            .getClassPlanApi()
            .getGradPlans(
                filter.getMinCreditsRequired(),
                filter.getMaxCreditsRequired(),
                filter.getMinCreditsCompleted(),
                filter.getMaxCreditsCompleted(),
                filter.getMinCGPA(),
                filter.getMaxCGPA(),
                levels,
                filter.getDegree(),
                filter.getMajor(),
                terms,
                filter.getYearStart(),
                filter.getYearEnd()
            )
            .enqueue(new Callback<GradPlansResponse>() {
                @Override
                public void onResponse(Call<GradPlansResponse> call, Response<GradPlansResponse> response) {
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    
                    if (response.isSuccessful() && response.body() != null) {
                        recyclerView.setAdapter(new GradPlansAdapter(response.body().getGradPlans()));
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