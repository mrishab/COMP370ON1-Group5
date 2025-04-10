package io.trishul.classplanner.ui.classes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.trishul.classplanner.network.dtos.ClassPlanDTO;

public class ClassesViewModel extends ViewModel {
    private final MutableLiveData<ClassesFilterRequest> currentFilter = new MutableLiveData<>(new ClassesFilterRequest());
    private final MutableLiveData<Boolean> filtersApplied = new MutableLiveData<>(false);
    private final MutableLiveData<ClassPlanDTO.Get> classPlan = new MutableLiveData<>();

    public MutableLiveData<ClassesFilterRequest> getCurrentFilter() {
        return currentFilter;
    }

    public void setCurrentFilter(ClassesFilterRequest filter) {
        this.currentFilter.setValue(filter);
    }

    public MutableLiveData<Boolean> getFiltersApplied() {
        return filtersApplied;
    }

    public void setFiltersApplied(boolean applied) {
        this.filtersApplied.setValue(applied);
    }

    public MutableLiveData<ClassPlanDTO.Get> getClassPlan() {
        return classPlan;
    }

    public void setClassPlan(ClassPlanDTO.Get plan) {
        this.classPlan.setValue(plan);
    }
}