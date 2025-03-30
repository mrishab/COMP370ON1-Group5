package io.trishul.classplanner.ui.gradplans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import io.trishul.classplanner.databinding.FragmentGradPlansBinding;

public class GradPlansFragment extends Fragment {

private FragmentGradPlansBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        GradPlansViewModel gradPlanViewModel =
                new ViewModelProvider(this).get(GradPlansViewModel.class);

    binding = FragmentGradPlansBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        gradPlanViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}