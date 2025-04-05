package io.trishul.classplanner.ui.gradplans.upload;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import io.trishul.classplanner.databinding.FragmentUploadGradPlanBinding;

public class UploadGradPlanFragment extends Fragment {
    private FragmentUploadGradPlanBinding binding;
    private UploadGradPlanViewModel viewModel;
    private ActivityResultLauncher<Intent> filePicker;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUploadGradPlanBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(UploadGradPlanViewModel.class);
        
        setupFilePicker();
        setupClickListeners();
        observeViewModel();
        
        return binding.getRoot();
    }

    private void setupFilePicker() {
        filePicker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    handleFileSelection(result.getData().getData());
                }
            });
    }

    private void setupClickListeners() {
        binding.uploadArea.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            filePicker.launch(intent);
        });

        binding.fabUpload.setOnClickListener(v -> {
            if (viewModel.getSelectedFileUri().getValue() != null) {
                uploadFile();
            } else {
                Snackbar.make(binding.getRoot(), "Please select a file first", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void observeViewModel() {
        viewModel.getSelectedFileName().observe(getViewLifecycleOwner(), fileName -> {
            binding.tvFileName.setText(fileName != null ? fileName : "Select a PDF file");
        });

        viewModel.getIsUploading().observe(getViewLifecycleOwner(), isUploading -> {
            binding.progressIndicator.setVisibility(isUploading ? View.VISIBLE : View.GONE);
            binding.fabUpload.setEnabled(!isUploading);
            binding.uploadArea.setEnabled(!isUploading);
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            binding.errorText.setText(error);
            binding.errorText.setVisibility(error != null ? View.VISIBLE : View.GONE);
        });
    }

    private void handleFileSelection(Uri uri) {
        if (uri != null) {
            String fileName = uri.getLastPathSegment();
            viewModel.setSelectedFile(uri, fileName);
        }
    }

    private void uploadFile() {
        // TODO: Implement actual file upload logic here
        viewModel.setIsUploading(true);
        // Simulate upload
        binding.getRoot().postDelayed(() -> {
            viewModel.setIsUploading(false);
            requireActivity().finish();
        }, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
