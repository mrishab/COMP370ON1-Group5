package io.trishul.classplanner.ui.classplans;

import android.content.Intent;
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

import io.trishul.classplanner.CreateNewClassPlanActivity;
import io.trishul.classplanner.R;
import io.trishul.classplanner.databinding.FragmentClassPlansBinding;
import io.trishul.classplanner.network.ApiClientManager;
import io.trishul.classplanner.network.dtos.ClassPlanDTO;
import io.trishul.classplanner.network.dtos.UserDTO;
import io.trishul.classplanner.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

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

        binding.fabAddClassPlan.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CreateNewClassPlanActivity.class);
            startActivity(intent);
        });

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

        SessionManager sessionManager = new SessionManager(requireContext());
        UserDTO.Get user = new UserDTO.Get();
        user.setFirstName(sessionManager.getUserInfo(UserDTO.ATTR_FIRST_NAME, ""));
        user.setLastName(sessionManager.getUserInfo(UserDTO.ATTR_LAST_NAME, ""));
        user.setEmail(sessionManager.getUserInfo(UserDTO.ATTR_EMAIL, ""));

        String idStr = sessionManager.getUserInfo(UserDTO.ATTR_ID, null);
        user.setId(idStr != null ? Long.parseLong(idStr) : null);

        if (user.getId() == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        String burdenCapacity = filter.getBurdenCapacity() != null ? 
            filter.getBurdenCapacity().get(0) : null; // Take first since API expects single value
        String classDistribution = filter.getClassDistribution() != null ? 
            filter.getClassDistribution().get(0) : null; // Take first since API expects single value

        Long gradPlanId = filter.getGradPlanIds() != null && !filter.getGradPlanIds().isEmpty() ?
            filter.getGradPlanIds().get(0).longValue() : null; // Take first since API expects single value

        ApiClientManager.getInstance(requireContext())
            .getClassPlanApi()
            .getPlans(
                filter.getDescription(),
                classDistribution,
                burdenCapacity,
                gradPlanId
            )
            .enqueue(new Callback<List<ClassPlanDTO.Get>>() {
                @Override
                public void onResponse(Call<List<ClassPlanDTO.Get>> call, Response<List<ClassPlanDTO.Get>> response) {
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    
                    if (response.isSuccessful() && response.body() != null) {
                        recyclerView.setAdapter(new ClassPlansAdapter(response.body()));
                    } else {
                        Toast.makeText(requireContext(), 
                            getString(R.string.error_load_class_plans, response.code()),
                            Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ClassPlanDTO.Get>> call, Throwable t) {
                    t.printStackTrace();
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(requireContext(), 
                        getString(R.string.error_load_class_plans_network, t.getMessage()),
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