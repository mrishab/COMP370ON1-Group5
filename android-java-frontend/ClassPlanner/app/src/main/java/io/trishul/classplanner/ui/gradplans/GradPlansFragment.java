package io.trishul.classplanner.ui.gradplans;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.trishul.classplanner.R;
import io.trishul.classplanner.UploadGradPlanActivity;
import io.trishul.classplanner.databinding.FragmentGradPlansBinding;

import io.trishul.classplanner.network.ApiClientManager;
import io.trishul.classplanner.network.dtos.GradPlanDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class GradPlansFragment extends Fragment {

    private FragmentGradPlansBinding binding;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GradPlansViewModel gradPlanViewModel;
    private GradPlansViewModel activityViewModel;
    private TextView emptyView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        gradPlanViewModel = new ViewModelProvider(this).get(GradPlansViewModel.class);
        activityViewModel = new ViewModelProvider(requireActivity()).get(GradPlansViewModel.class);

        binding = FragmentGradPlansBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerViewGradPlans;
        swipeRefreshLayout = binding.swipeRefreshLayout;
        emptyView = root.findViewById(R.id.empty_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this::fetchGradPlans);

        // Observe filter changes from activity ViewModel
        activityViewModel.getCurrentFilter().observe(getViewLifecycleOwner(), filter -> {
            if (activityViewModel.getFiltersApplied().getValue() == Boolean.TRUE) {
                fetchGradPlans();
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab_add_grad_plan);
        fab.setOnClickListener(v -> startActivity(new Intent(requireContext(), UploadGradPlanActivity.class)));

        fetchGradPlans();
        return root;
    }

    private void fetchGradPlans() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        GradPlanFilterRequest filter = activityViewModel.getCurrentFilter().getValue();

        // Convert lists to comma-separated strings for API
        String levels = filter.getLevels() != null ? String.join(",", filter.getLevels()) : null;
        String terms = filter.getTerms() != null ? String.join(",", filter.getTerms()) : null;

        ApiClientManager.getInstance(requireContext())
            .getGradPlanApi()
            .getPlans(null, null, null, null, null, null, null)  // Use getPlans() instead of getGradPlans() with parameters
            .enqueue(new Callback<List<GradPlanDTO.Get>>() {
                @Override
                public void onResponse(Call<List<GradPlanDTO.Get>> call, Response<List<GradPlanDTO.Get>> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    
                    if (response.isSuccessful() && response.body() != null) {
                        List<GradPlanDTO.Get> gradPlans = response.body();
                        if (gradPlans.isEmpty()) {
                            emptyView.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(new GradPlansAdapter(gradPlans));
                        }
                    } else {
                        Toast.makeText(requireContext(), 
                            getString(R.string.error_load_grad_plans, response.code()), 
                            Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<GradPlanDTO.Get>> call, Throwable t) {
                    t.printStackTrace();
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