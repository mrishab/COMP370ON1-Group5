package io.trishul.classplanner.ui.classplans;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.trishul.classplanner.api.models.ClassPlanFilterRequest;

public class ClassPlansViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    private final MutableLiveData<ClassPlanFilterRequest> currentFilter = new MutableLiveData<>(new ClassPlanFilterRequest());
    private final MutableLiveData<Boolean> filtersApplied = new MutableLiveData<>(false);

    public ClassPlansViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<ClassPlanFilterRequest> getCurrentFilter() {
        return currentFilter;
    }

    public void setCurrentFilter(ClassPlanFilterRequest filter) {
        this.currentFilter.setValue(filter);
    }

    public MutableLiveData<Boolean> getFiltersApplied() {
        return filtersApplied;
    }

    public void setFiltersApplied(boolean applied) {
        this.filtersApplied.setValue(applied);
    }
}