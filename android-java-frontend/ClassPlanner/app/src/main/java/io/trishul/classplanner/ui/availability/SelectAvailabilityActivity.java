package io.trishul.classplanner.ui.availability;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import io.trishul.classplanner.R;

public class SelectAvailabilityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_availability);

    }

    public void onAvailabilityToggleButton(View view) {
    }
}