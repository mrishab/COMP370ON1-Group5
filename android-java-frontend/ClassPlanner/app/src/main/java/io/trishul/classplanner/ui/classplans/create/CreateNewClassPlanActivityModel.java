package io.trishul.classplanner.ui.classplans.create;

import android.net.Uri;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreateNewClassPlanActivityModel extends ViewModel {
    // Grad plan URI logic
    private final MutableLiveData<Uri> gradPlanUri = new MutableLiveData<>();

    public void setGradPlanUri(Uri uri) {
        gradPlanUri.setValue(uri);
    }

    public MutableLiveData<Uri> getGradPlanUri() {
        return gradPlanUri;
    }

    // Preferences logic
    private final MutableLiveData<Integer> desiredNumberOfClasses = new MutableLiveData<>(5);
    private final MutableLiveData<String> burdenCapacity = new MutableLiveData<>("MEDIUM");
    private final MutableLiveData<String> classDistribution = new MutableLiveData<>("CONCENTRATED");

    public MutableLiveData<Integer> getDesiredNumberOfClasses() {
        return desiredNumberOfClasses;
    }
    public void setDesiredNumberOfClasses(Integer desiredNumberOfClasses) {
        this.desiredNumberOfClasses.setValue(desiredNumberOfClasses);
    }

    public MutableLiveData<String> getBurdenCapacity() {
        return burdenCapacity;
    }
    public void setBurdenCapacity(String burdenCapacity) {
        this.burdenCapacity.setValue(burdenCapacity.toUpperCase());
    }

    public MutableLiveData<String> getClassDistribution() {
        return classDistribution;
    }
    public void setClassDistribution(String classDistribution) {
        this.classDistribution.setValue(classDistribution.toUpperCase());
    }

    // Availability logic
    public static final int NUM_OF_HOURS = 24;

    public MutableLiveData<Map<String, boolean[]>> getAvailability() {
        return availability;
    }

    private final MutableLiveData<Map<String, boolean[]>> availability = new MutableLiveData<>(initAvailabilityMap());

    private static Map<String, boolean[]> initAvailabilityMap() {
        Map<String, boolean[]> availabilityMap = new HashMap<>();
        String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
        for (String day : days) {
            boolean[] dayArray = new boolean[NUM_OF_HOURS];
            Arrays.fill(dayArray, false);
            availabilityMap.put(day, dayArray);
        }
        return availabilityMap;
    }

    public boolean toggleAvailabilityForDayAndHour(String day, int hour) {
        Map<String, boolean[]> map = availability.getValue();
        day = day.toUpperCase();
        map.get(day)[hour] = !map.get(day)[hour];
        availability.setValue(map);
        return map.get(day)[hour];
    }

    public boolean getAvailabilityForDayAndHour(String day, int hour) {
        return availability.getValue().get(day.toUpperCase())[hour];
    }

    public boolean isMinimumAvailabilitySelected() {
        Map<String, boolean[]> map = availability.getValue();
        for (String day : map.keySet()) {
            boolean[] dayAvailability = map.get(day);
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
