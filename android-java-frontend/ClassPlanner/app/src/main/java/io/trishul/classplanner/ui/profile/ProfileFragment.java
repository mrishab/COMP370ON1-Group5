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

public class ProfileFragment extends Fragment {

    private TextView textNotifications;
    private Button logoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textNotifications = view.findViewById(R.id.text_notifications);
        logoutButton = view.findViewById(R.id.logoutButton);

        SharedPreferences prefs = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String email = prefs.getString("userEmail", null);

        if (email != null) {
            textNotifications.setText("Logged in as: " + email);
        } else {
            textNotifications.setText("Not logged in.");
        }

        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("userEmail"); 
            editor.apply();

            Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); 
            startActivity(intent);
        });

        return view;
    }
}