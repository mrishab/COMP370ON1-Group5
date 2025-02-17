package io.trishul.classplanner.ui.courseplans;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.trishul.classplanner.CreateNewCoursePlanActivity;
import io.trishul.classplanner.databinding.FragmentCoursePlansBinding;

public class CoursePlansFragment extends Fragment {

private FragmentCoursePlansBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        CoursePlansViewModel coursePlansViewModel =
                new ViewModelProvider(this).get(CoursePlansViewModel.class);

        binding = FragmentCoursePlansBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button createNewCoursePlanButton = binding.buttonCreateCoursePlan;

        createNewCoursePlanButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateNewCoursePlanActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}