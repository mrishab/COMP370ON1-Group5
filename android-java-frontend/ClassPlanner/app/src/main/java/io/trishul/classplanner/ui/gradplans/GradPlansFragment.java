package io.trishul.classplanner.ui.gradplans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.trishul.classplanner.databinding.FragmentGradPlansBinding;
import io.trishul.classplanner.api.models.GradPlansResponse;

import io.trishul.classplanner.network.ApiClientManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradPlansFragment extends Fragment {

private FragmentGradPlansBinding binding;
private RecyclerView recyclerView;
private SwipeRefreshLayout swipeRefreshLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        GradPlansViewModel gradPlanViewModel =
                new ViewModelProvider(this).get(GradPlansViewModel.class);

    binding = FragmentGradPlansBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        recyclerView = binding.recyclerViewGradPlans;
        swipeRefreshLayout = binding.swipeRefreshLayout;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this::fetchGradPlans);

        fetchGradPlans();
        return root;
    }

private void fetchGradPlans() {
    recyclerView.setVisibility(View.GONE);

    ApiClientManager.getInstance(requireContext())
        .getClassPlanApi()
        .getGradPlans()
        .enqueue(new Callback<GradPlansResponse>() {
            @Override
            public void onResponse(Call<GradPlansResponse> call, Response<GradPlansResponse> response) {
                recyclerView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setAdapter(new GradPlansAdapter(response.body().getGradPlans()));
                }
            }

            @Override
            public void onFailure(Call<GradPlansResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                // Handle error
            }
        });
}

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}