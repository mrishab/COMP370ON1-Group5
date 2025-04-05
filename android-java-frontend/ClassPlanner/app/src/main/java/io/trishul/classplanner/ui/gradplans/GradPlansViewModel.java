package io.trishul.classplanner.ui.gradplans;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.trishul.classplanner.api.models.GradPlanFilterRequest;

public class GradPlansViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<GradPlanFilterRequest> currentFilter = new MutableLiveData<>(new GradPlanFilterRequest());
    private final MutableLiveData<Boolean> filtersApplied = new MutableLiveData<>(false);

    public GradPlansViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<GradPlanFilterRequest> getCurrentFilter() {
        return currentFilter;
    }

    public void setCurrentFilter(GradPlanFilterRequest filter) {
        this.currentFilter.setValue(filter);
    }

    public MutableLiveData<Boolean> getFiltersApplied() {
        return filtersApplied;
    }

    public void setFiltersApplied(boolean applied) {
        this.filtersApplied.setValue(applied);
    }
}