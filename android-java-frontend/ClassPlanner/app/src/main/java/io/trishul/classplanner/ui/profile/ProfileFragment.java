package io.trishul.classplanner.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.trishul.classplanner.R;
import io.trishul.classplanner.ui.login.LoginActivity;
import io.trishul.classplanner.utils.SessionManager;

public class ProfileFragment extends Fragment {

    private TextView textProfileInfo;
    private Button logoutButton;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sessionManager = new SessionManager(requireContext());

        textProfileInfo = view.findViewById(R.id.text_profile_info);
        logoutButton = view.findViewById(R.id.logoutButton);

        displayUserProfile();
        setupLogoutButton();

        return view;
    }

    private void displayUserProfile() {
        SharedPreferences prefs = requireContext().getSharedPreferences("ClassPlannerPrefs", Context.MODE_PRIVATE);
        String firstName = prefs.getString("userFirstName", "N/A");
        String lastName = prefs.getString("userLastName", "N/A");
        String email = prefs.getString("userEmail", "N/A");

        StringBuilder profileInfo = new StringBuilder();
        profileInfo.append("Name\n")
                  .append(firstName)
                  .append(" ")
                  .append(lastName)
                  .append("\n\n")
                  .append("Email\n")
                  .append(email)
                  .append("\n\n")
                  .append("Student Status\n")
                  .append("Active");

        textProfileInfo.setText(profileInfo.toString());
    }

    private void setupLogoutButton() {
        logoutButton.setOnClickListener(v -> {
            sessionManager.clearSession();
            Toast.makeText(requireContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
            
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}