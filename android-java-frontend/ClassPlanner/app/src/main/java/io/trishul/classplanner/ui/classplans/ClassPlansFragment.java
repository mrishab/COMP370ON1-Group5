package io.trishul.classplanner.ui.classplans;

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

import io.trishul.classplanner.databinding.FragmentClassPlansBinding;
import io.trishul.classplanner.api.models.ClassPlanFilterRequest;
import io.trishul.classplanner.network.ApiClientManager;
import io.trishul.classplanner.network.ClassPlansResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassPlansFragment extends Fragment {
    private FragmentClassPlansBinding binding;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ClassPlansViewModel classPlansViewModel;
    private ClassPlansViewModel activityViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        classPlansViewModel = new ViewModelProvider(this).get(ClassPlansViewModel.class);
        activityViewModel = new ViewModelProvider(requireActivity()).get(ClassPlansViewModel.class);

        binding = FragmentClassPlansBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerViewClassPlans;
        swipeRefreshLayout = binding.swipeRefreshLayout;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this::fetchClassPlans);

        // Observe filter changes from activity ViewModel
        activityViewModel.getCurrentFilter().observe(getViewLifecycleOwner(), filter -> {
            if (activityViewModel.getFiltersApplied().getValue() == Boolean.TRUE) {
                fetchClassPlans();
            }
        });

        fetchClassPlans();
        return root;
    }

    private void fetchClassPlans() {
        recyclerView.setVisibility(View.GONE);
        ClassPlanFilterRequest filter = activityViewModel.getCurrentFilter().getValue();

        // Convert lists to comma-separated strings for API
        String gradPlanIds = filter.getGradPlanIds() != null ? 
            String.join(",", filter.getGradPlanIds().stream().map(String::valueOf).toArray(String[]::new)) : null;
        String terms = filter.getTerms() != null ? String.join(",", filter.getTerms()) : null;
        String burdenCapacity = filter.getBurdenCapacity() != null ? String.join(",", filter.getBurdenCapacity()) : null;
        String classDistribution = filter.getClassDistribution() != null ? String.join(",", filter.getClassDistribution()) : null;

        ApiClientManager.getInstance(requireContext())
            .getClassPlanApi()
            .getClassPlans(
                gradPlanIds,
                filter.getProgramName(),
                filter.getDescription(),
                filter.getMinCourses(),
                filter.getMaxCourses(),
                filter.getMinCredits(),
                filter.getMaxCredits(),
                terms,
                filter.getYearStart(),
                filter.getYearEnd(),
                burdenCapacity,
                classDistribution
            )
            .enqueue(new Callback<ClassPlansResponse>() {
                @Override
                public void onResponse(Call<ClassPlansResponse> call, Response<ClassPlansResponse> response) {
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    
                    if (response.isSuccessful() && response.body() != null) {
                        recyclerView.setAdapter(new ClassPlansAdapter(response.body().getClassPlans()));
                    } else {
                        Toast.makeText(requireContext(), 
                            "Failed to load class plans: " + response.code(), 
                            Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ClassPlansResponse> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(requireContext(), 
                        "Error loading class plans: " + t.getMessage(),
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