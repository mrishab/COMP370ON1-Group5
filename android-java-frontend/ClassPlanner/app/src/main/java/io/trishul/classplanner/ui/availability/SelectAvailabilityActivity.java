package io.trishul.classplanner.ui.availability;

import android.os.Bundle;
import android.view.View;

import io.trishul.classplanner.R;
import io.trishul.classplanner.ui.base.BaseActivity;

public class SelectAvailabilityActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_availability);

    }

    public void onAvailabilityToggleButton(View view) {
    }
}