package io.trishul.classplanner.ui.termplan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TermPlanViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TermPlanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}