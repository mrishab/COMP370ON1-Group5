package io.trishul.classplanner.ui.courseplans.create.availability;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SelectAvailabilityModel extends ViewModel {
    public static final int NUM_OF_HOURS = 24;
    private static Map<String, boolean[]> initAvailabilityMap() {
        Map<String, boolean[]> availabilityMap = new HashMap<>();
        String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
        for (String day : days) {
            boolean[] availability = new boolean[NUM_OF_HOURS];
            Arrays.fill(availability, false);
            availabilityMap.put(day, availability);
        }
        return availabilityMap;
    }

    private final MutableLiveData<Map<String, boolean[]>> availability;

    public SelectAvailabilityModel() {
        this.availability = new MutableLiveData<>(initAvailabilityMap());
    }

    public boolean toggleAvailabilityForDayAndHour(String day, int hour) {
        day = day.toUpperCase();
        Map<String, boolean[]> availabilityMap = this.availability.getValue();
        availabilityMap.get(day)[hour] = !availabilityMap.get(day)[hour];
        this.availability.setValue(availabilityMap);

        return availabilityMap.get(day)[hour];
    }

    public MutableLiveData<Map<String, boolean[]>> getAvailability() {
        return availability;
    }

    public boolean getAvailabilityForDayAndHour(String day, int hour) {
        day = day.toUpperCase();
        return this.availability.getValue().get(day)[hour];
    }

    public boolean isMinimumAvailabilitySelected() {
        Map<String, boolean[]> availabilityMap = this.availability.getValue();
        for (String day : availabilityMap.keySet()) {
            boolean[] dayAvailability = availabilityMap.get(day);
            int consecutiveCount = 0;
            for (boolean isAvailable : dayAvailability) {
                if (isAvailable) {
                    consecutiveCount++;
                    if (consecutiveCount >= 3) {
                        return true;
                    }
                } else {
                    consecutiveCount = 0;
                }
            }
        }
        return false;
    }
}
