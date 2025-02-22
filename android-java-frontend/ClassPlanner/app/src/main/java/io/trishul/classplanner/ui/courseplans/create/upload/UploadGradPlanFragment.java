package io.trishul.classplanner.ui.courseplans.create.upload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.trishul.classplanner.databinding.FragmentUploadGradPlanBinding;
import io.trishul.classplanner.R;

public class UploadGradPlanFragment extends Fragment {
    private FragmentUploadGradPlanBinding binding;
    private ActivityResultLauncher<Intent> filePicker;
    private Button nextButton;
    private TextView selectedFileNameTextView;
    private UploadGradPlanModel uploadGradPlanModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        this.uploadGradPlanModel =
                new ViewModelProvider(this).get(UploadGradPlanModel.class);

        this.binding = FragmentUploadGradPlanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.nextButton = getActivity().findViewById(R.id.button_create_course_plan_next);
        this.selectedFileNameTextView = binding.textUploadGradPlanFileName;

        activateNextButtonIfReady();
        setSelectedFileName();

        filePicker = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Uri fileUri = data.getData();
                uploadGradPlanModel.setGradPlanUri(fileUri);
                setSelectedFileName();
                activateNextButtonIfReady();
            }
        });

        ImageButton fileUploadButton = binding.imgButtonUploadGradPlan;
        fileUploadButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            filePicker.launch(intent);
        });

        return root;
    }

    private void activateNextButtonIfReady() {
        nextButton.setEnabled(uploadGradPlanModel.getGradPlanUri().getValue() != null);
    }

    private void setSelectedFileName() {
        Uri fileUri = uploadGradPlanModel.getGradPlanUri().getValue();
        if (fileUri != null) {
            String fileName = getFileName(requireContext(), fileUri);
            if (fileName != null) {
                selectedFileNameTextView.setText(fileName);
            }
        }
    }

    private String getFileName(Context context, Uri uri) {
        String fileName = null;
        String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
            fileName = cursor.getString(columnIndex);
            cursor.close();
        }
        return fileName;
    }


@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}