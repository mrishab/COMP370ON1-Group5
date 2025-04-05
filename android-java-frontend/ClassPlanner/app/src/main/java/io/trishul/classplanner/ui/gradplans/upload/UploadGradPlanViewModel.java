package io.trishul.classplanner.ui.gradplans.upload;

import android.net.Uri;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UploadGradPlanViewModel extends ViewModel {
    private final MutableLiveData<Uri> selectedFileUri = new MutableLiveData<>();
    private final MutableLiveData<String> selectedFileName = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isUploading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public void setSelectedFile(Uri uri, String fileName) {
        selectedFileUri.setValue(uri);
        selectedFileName.setValue(fileName);
        errorMessage.setValue(null);
    }

    public LiveData<Uri> getSelectedFileUri() {
        return selectedFileUri;
    }

    public LiveData<String> getSelectedFileName() {
        return selectedFileName;
    }

    public LiveData<Boolean> getIsUploading() {
        return isUploading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setIsUploading(boolean uploading) {
        isUploading.setValue(uploading);
    }

    public void setErrorMessage(String message) {
        errorMessage.setValue(message);
    }
}
