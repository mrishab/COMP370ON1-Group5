package io.trishul.classplanner.ui.courseplans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.trishul.classplanner.databinding.FragmentCoursePlansBinding;

public class CoursePlansFragment extends Fragment {

private FragmentCoursePlansBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        CoursePlansViewModel coursePlansViewModel =
                new ViewModelProvider(this).get(CoursePlansViewModel.class);

    binding = FragmentCoursePlansBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textHome;
        coursePlansViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}