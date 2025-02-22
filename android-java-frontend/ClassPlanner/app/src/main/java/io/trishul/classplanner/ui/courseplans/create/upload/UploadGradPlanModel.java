package io.trishul.classplanner.ui.courseplans.create.upload;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UploadGradPlanModel extends ViewModel {
    private MutableLiveData<Uri> gradPlanUri = new MutableLiveData<>();

    public void setGradPlanUri(Uri uri) {
        gradPlanUri.setValue(uri);
    }

    public MutableLiveData<Uri> getGradPlanUri() {
        return gradPlanUri;
    }
}
