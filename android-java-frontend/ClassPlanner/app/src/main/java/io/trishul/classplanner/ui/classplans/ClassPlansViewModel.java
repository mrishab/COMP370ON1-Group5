package io.trishul.classplanner.ui.classplans;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClassPlansViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ClassPlansViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}