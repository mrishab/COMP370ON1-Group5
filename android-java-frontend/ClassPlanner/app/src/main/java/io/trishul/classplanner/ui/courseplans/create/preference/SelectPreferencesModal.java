package io.trishul.classplanner.ui.courseplans.create.preference;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectPreferencesModal extends ViewModel {
    private final MutableLiveData<Integer> desiredNumberOfCourses;
    private final MutableLiveData<String> burdenCapacity;
    private final MutableLiveData<String> classDistribution;

    public SelectPreferencesModal() {
        this.desiredNumberOfCourses = new MutableLiveData<>(5);
        this.burdenCapacity = new MutableLiveData<>("MEDIUM");
        this.classDistribution = new MutableLiveData<>("CONCENTRATED");
    }

    public MutableLiveData<Integer> getDesiredNumberOfCourses() {
        return desiredNumberOfCourses;
    }

    public void setDesiredNumberOfCourses(Integer desiredNumberOfCourses) {
        this.desiredNumberOfCourses.setValue(desiredNumberOfCourses);
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
}
