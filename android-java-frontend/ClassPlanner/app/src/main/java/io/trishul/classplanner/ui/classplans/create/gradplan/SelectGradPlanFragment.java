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

import java.util.List;

import io.trishul.classplanner.R;
import io.trishul.classplanner.databinding.FragmentSelectGradPlanBinding;
import io.trishul.classplanner.network.ApiClientManager;
import io.trishul.classplanner.network.dtos.GradPlanDTO;
import io.trishul.classplanner.ui.classplans.create.CreateNewClassPlanActivityModel;
import io.trishul.classplanner.ui.gradplans.GradPlansAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectGradPlanFragment extends Fragment {
    private FragmentSelectGradPlanBinding binding;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CreateNewClassPlanActivityModel createNewClassPlanActivityModel;
    private Button nextButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
        this.createNewClassPlanActivityModel = 
            new ViewModelProvider(requireActivity()).get(CreateNewClassPlanActivityModel.class);
        this.nextButton = getActivity().findViewById(R.id.button_create_class_plan_next);

        binding = FragmentSelectGradPlanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerViewGradPlans;
        swipeRefreshLayout = binding.swipeRefreshLayout;

        setupRecyclerView();
        setupSwipeRefresh();
        observeViewModel();
        fetchGradPlans();
        
        return root;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this::fetchGradPlans);
    }

    private void observeViewModel() {
        createNewClassPlanActivityModel.getSelectedGradPlanId().observe(
            getViewLifecycleOwner(), 
            gradPlanId -> nextButton.setEnabled(gradPlanId != null)
        );
    }

    private void fetchGradPlans() {
        recyclerView.setVisibility(View.GONE);
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }

        ApiClientManager.getInstance(requireContext())
            .getGradPlanApi()
            .getPlans(null, null, null, null, null, null, null)
            .enqueue(new Callback<List<GradPlanDTO.Get>>() {
                @Override
                public void onResponse(Call<List<GradPlanDTO.Get>> call, Response<List<GradPlanDTO.Get>> response) {
                    handleApiResponse(response);
                }

                @Override
                public void onFailure(Call<List<GradPlanDTO.Get>> call, Throwable t) {
                    t.printStackTrace();
                    handleApiError(t);
                }
            });
    }

    private void handleApiResponse(Response<List<GradPlanDTO.Get>> response) {
        swipeRefreshLayout.setRefreshing(false);
        
        if (response.isSuccessful() && response.body() != null) {
            recyclerView.setVisibility(View.VISIBLE);
            GradPlansAdapter adapter = new SelectableGradPlansAdapter(
                response.body(),
                createNewClassPlanActivityModel
            );
            recyclerView.setAdapter(adapter);
        } else {
            showError(getString(R.string.error_load_grad_plans, response.code()));
        }
    }

    private void handleApiError(Throwable t) {
        t.printStackTrace();
        swipeRefreshLayout.setRefreshing(false);
        showError(getString(R.string.error_load_grad_plans_network, t.getMessage()));
    }

    private void showError(String message) {
        recyclerView.setVisibility(View.GONE);
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
