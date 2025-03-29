package io.trishul.classplanner.ui.classplans.create.availability;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import io.trishul.classplanner.R;
import io.trishul.classplanner.databinding.FragmentSelectAvailabilityBinding;
import io.trishul.classplanner.ui.classplans.create.CreateNewClassPlanActivityModel;

public class SelectAvailabilityFragment extends Fragment {
    private CreateNewClassPlanActivityModel createNewClassPlanActivityModel;
    private FragmentSelectAvailabilityBinding binding;
    private Button nextButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.createNewClassPlanActivityModel =
                new ViewModelProvider(requireActivity()).get(CreateNewClassPlanActivityModel.class);

        this.nextButton = getActivity().findViewById(R.id.button_create_class_plan_next);

        binding = FragmentSelectAvailabilityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setNextButtonActiveIfReady();
        getAvailabilityButtons(root);

        return root;
    }

    @Override
    public void onHiddenChanged(boolean isHidden) {
        super.onHiddenChanged(isHidden);
        setNextButtonActiveIfReady();
    }

    private void setNextButtonActiveIfReady() {
        if (createNewClassPlanActivityModel.isMinimumAvailabilitySelected()) {
            nextButton.setEnabled(true);
        } else {
            nextButton.setEnabled(false);
        }
    }


    private List<ToggleButton> getAvailabilityButtons(View root) {
        List<ToggleButton> availabilityButtons = new ArrayList<>();
        TableLayout availabilityTableLayout = root.findViewById(R.id.availability_table);
        for (int i = 1; i < availabilityTableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) availabilityTableLayout.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                View view = row.getChildAt(j);
                view.setOnClickListener(this::onAvailabilityToggleButton);
                availabilityButtons.add((ToggleButton) view);
            }
        }
        return availabilityButtons;
    }

    private void initButtonState(ToggleButton button) {
        String tag = (String) button.getTag();
        String[] tagParts = tag.split(";");
        String day = tagParts[0];
        int hour = Integer.parseInt(tagParts[1]);

        button.setChecked(createNewClassPlanActivityModel.getAvailabilityForDayAndHour(day, hour));
    }


    public void onAvailabilityToggleButton(View view) {
        String tag = (String) view.getTag();

        String[] tagParts = tag.split(";");
        String day = tagParts[0];
        int hour = Integer.parseInt(tagParts[1]);

        createNewClassPlanActivityModel.toggleAvailabilityForDayAndHour(day, hour);
        setNextButtonActiveIfReady();
    }
}
