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

import java.io.InputStream;

import io.trishul.classplanner.network.ApiClientManager;
import io.trishul.classplanner.network.dtos.GradPlanDTO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Uri fileUri = viewModel.getSelectedFileUri().getValue();
        if (fileUri == null) {
            Snackbar.make(binding.getRoot(), "No file selected", Snackbar.LENGTH_SHORT).show();
            return;
        }

        String fileName = viewModel.getSelectedFileName().getValue();
        viewModel.setIsUploading(true);

        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(fileUri);
            byte[] fileBytes = new byte[inputStream.available()];
            inputStream.read(fileBytes);
            inputStream.close();

            RequestBody fileNamePart = RequestBody.create(fileName, okhttp3.MultipartBody.FORM);
            RequestBody filePart = RequestBody.create(fileBytes, okhttp3.MediaType.parse("application/pdf"));
            MultipartBody.Part file = MultipartBody.Part.createFormData("file", fileName, filePart);

            ApiClientManager.getInstance(requireContext())
                .getGradPlanApi()
                .createPlan(fileNamePart, file)
                .enqueue(new Callback<GradPlanDTO.Get>() {
                    @Override
                    public void onResponse(Call<GradPlanDTO.Get> call, Response<GradPlanDTO.Get> response) {
                        viewModel.setIsUploading(false);
                        if (response.isSuccessful()) {
                            Snackbar.make(binding.getRoot(), "File uploaded successfully", Snackbar.LENGTH_SHORT).show();
                            requireActivity().finish();
                        } else {
                            Snackbar.make(binding.getRoot(), "Upload failed: " + response.code(), Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GradPlanDTO.Get> call, Throwable t) {
                        viewModel.setIsUploading(false);
                        Snackbar.make(binding.getRoot(), "Upload failed: " + t.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
        } catch (Exception e) {
            viewModel.setIsUploading(false);
            Snackbar.make(binding.getRoot(), "Error reading file: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
