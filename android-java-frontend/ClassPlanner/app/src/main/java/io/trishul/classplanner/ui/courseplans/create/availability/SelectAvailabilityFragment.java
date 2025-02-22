package io.trishul.classplanner.ui.courseplans.create.availability;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.trishul.classplanner.R;
import io.trishul.classplanner.databinding.FragmentSelectAvailabilityBinding;

public class SelectAvailabilityFragment extends Fragment {
    private SelectAvailabilityModel selectAvailabilityModel;
    private List<ToggleButton> availabilityButtons;
    private FragmentSelectAvailabilityBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.selectAvailabilityModel =
                new ViewModelProvider(this).get(SelectAvailabilityModel.class);

        binding = FragmentSelectAvailabilityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.availabilityButtons = getAvailabilityButtons(root);


        return root;
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
                setToggleButtonChecked((ToggleButton) view);
            }
        }
        return availabilityButtons;
    }

    private void setToggleButtonChecked(ToggleButton toggleButton) {
        Map<String, Set<Integer>> availabilityMap = this.selectAvailabilityModel.getAvailability().getValue();
        String tag = (String) toggleButton.getTag();
        String[] tagParts = tag.split(";");
        String day = tagParts[0];
        Integer hour = Integer.parseInt(tagParts[1]);
        Set<Integer> hours = availabilityMap.computeIfAbsent(day, k -> new HashSet<>());

        if (hours.contains(hour)) {
            toggleButton.setChecked(true);
        } else {
            toggleButton.setChecked(false);
        }
    }

    public void onAvailabilityToggleButton(View view) {
        ToggleButton toggleButton = (ToggleButton) view;;
        Map<String, Set<Integer>> availabilityMap = this.selectAvailabilityModel.getAvailability().getValue();
        String tag = (String) view.getTag();

        String[] tagParts = tag.split(";");
        String day = tagParts[0];
        Integer hour = Integer.parseInt(tagParts[1]);


        Set<Integer> hours = availabilityMap.computeIfAbsent(day, k -> new HashSet<>());
        if (toggleButton.isChecked()) {
            hours.add(hour);
        } else {
            hours.remove(hour);
        }
    }
}
