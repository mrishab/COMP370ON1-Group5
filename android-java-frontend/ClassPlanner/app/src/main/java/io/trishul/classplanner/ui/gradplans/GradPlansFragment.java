package io.trishul.classplanner.ui.gradplans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.trishul.classplanner.databinding.FragmentGradPlansBinding;
import io.trishul.classplanner.api.BackendClient;
import io.trishul.classplanner.api.models.GradPlansRequest;

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

    GradPlansRequest request = new GradPlansRequest();
    // Set request parameters if needed

    BackendClient.getInstance().getGradPlans(request).thenAccept(response -> {
        recyclerView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);

        // Populate RecyclerView with response data
        recyclerView.setAdapter(new GradPlansAdapter(response.getGradPlans()));
    }).exceptionally(throwable -> {
        swipeRefreshLayout.setRefreshing(false);
        // Handle error
        return null;
    });
}

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}