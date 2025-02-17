package io.trishul.classplanner.ui.termplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import io.trishul.classplanner.databinding.FragmentTermPlanBinding;

public class TermPlanFragment extends Fragment {

private FragmentTermPlanBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        TermPlanViewModel termPlanViewModel =
                new ViewModelProvider(this).get(TermPlanViewModel.class);

    binding = FragmentTermPlanBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        termPlanViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}