package io.trishul.classplanner.ui.courseplans.create.availability;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectAvailabilityModel extends ViewModel {
    private MutableLiveData<Map<String, Set<Integer>>> availability = new MutableLiveData<>(new HashMap<>());

    public void setAvailability(Map<String, Set<Integer>> availability) {
        this.availability.setValue(availability);
    }

    public MutableLiveData<Map<String, Set<Integer>>> getAvailability() {
        return availability;
    }
}
