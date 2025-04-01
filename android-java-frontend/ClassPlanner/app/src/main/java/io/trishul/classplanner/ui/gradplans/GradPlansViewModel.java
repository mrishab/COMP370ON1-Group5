package io.trishul.classplanner.ui.gradplans;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GradPlansViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GradPlansViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}