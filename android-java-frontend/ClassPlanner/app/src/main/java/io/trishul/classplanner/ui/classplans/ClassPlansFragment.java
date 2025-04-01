package io.trishul.classplanner.ui.classplans;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.trishul.classplanner.CreateNewClassPlanActivity;
import io.trishul.classplanner.databinding.FragmentClassPlansBinding;

public class ClassPlansFragment extends Fragment {

private FragmentClassPlansBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        ClassPlansViewModel classPlansViewModel =
                new ViewModelProvider(this).get(ClassPlansViewModel.class);

        binding = FragmentClassPlansBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button createNewClassPlanButton = binding.buttonCreateClassPlan;

        createNewClassPlanButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateNewClassPlanActivity.class);
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