package io.trishul.classplanner.ui.classes;

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
import com.google.android.material.chip.Chip;

import io.trishul.classplanner.databinding.FragmentClassesBinding;
import io.trishul.classplanner.network.ApiClientManager;
import io.trishul.classplanner.network.dtos.ClassPlanDTO;
import io.trishul.classplanner.network.dtos.CourseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassesFragment extends Fragment {
    private FragmentClassesBinding binding;
    private ClassesViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ClassesViewModel.class);
        binding = FragmentClassesBinding.inflate(inflater, container, false);
        
        swipeRefreshLayout = binding.swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this::fetchClassPlan);
        
        // Observe filter changes
        viewModel.getCurrentFilter().observe(getViewLifecycleOwner(), filter -> {
            if (viewModel.getFiltersApplied().getValue() == Boolean.TRUE) {
                fetchClassPlan();
            }
        });

        return binding.getRoot();
    }

    private void fetchClassPlan() {
        ClassesFilterRequest filter = viewModel.getCurrentFilter().getValue();
        if (filter == null || filter.getClassPlanId() == null) {
            Toast.makeText(requireContext(), "No class plan selected", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        ApiClientManager.getInstance(requireContext())
            .getClassPlanApi()
            .getPlan(filter.getClassPlanId())
            .enqueue(new Callback<ClassPlanDTO.Get>() {
                @Override
                public void onResponse(Call<ClassPlanDTO.Get> call, Response<ClassPlanDTO.Get> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful() && response.body() != null) {
                        updateUI(response.body());
                    } else {
                        Toast.makeText(requireContext(), 
                            "Error loading class plan: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ClassPlanDTO.Get> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(requireContext(), 
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                }
            });
    }

    private void updateUI(ClassPlanDTO.Get classPlan) {
        // Update header info
        binding.tvProgramName.setText(classPlan.getGradPlan().getProgramName());
        binding.tvDescription.setText(classPlan.getDescription());
        binding.chipDistribution.setText(classPlan.getClassDistribution().toString());
        binding.chipBurden.setText(classPlan.getBurdenCapacity().toString());

        // Setup recycler view with courses
        RecyclerView recyclerView = binding.recyclerViewCourses;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        
        if (classPlan.getClasses() != null && !classPlan.getClasses().isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new CourseAdapter(classPlan.getClasses()));
        } else {
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(requireContext(), "No courses found in this class plan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}