package io.trishul.classplanner.ui.courseplans;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoursePlansViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CoursePlansViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}