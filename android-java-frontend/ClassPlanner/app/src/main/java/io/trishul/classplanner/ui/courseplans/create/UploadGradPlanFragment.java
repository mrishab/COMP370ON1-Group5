package io.trishul.classplanner.ui.courseplans.create;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        UploadGradPlanModel uploadGradPlanModel =
                new ViewModelProvider(this).get(UploadGradPlanModel.class);

        binding = FragmentUploadGradPlanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView selectedFileNameTextView = binding.textUploadGradPlanFileName;

        filePicker = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Uri fileUri = data.getData();
                if (fileUri != null && fileUri.toString().endsWith(".pdf")) {
                    selectedFileNameTextView.setText(fileUri.getLastPathSegment());
                } else {
                    selectedFileNameTextView.setText(getString(R.string.text_upload_grad_plan_selected_filetype_error));
                }
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


@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}