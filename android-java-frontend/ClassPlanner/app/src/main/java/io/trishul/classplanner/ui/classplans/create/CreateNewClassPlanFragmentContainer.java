package io.trishul.classplanner.ui.classplans.create;

import androidx.fragment.app.Fragment;

import io.trishul.classplanner.ui.classplans.create.availability.SelectAvailabilityFragment;
import io.trishul.classplanner.ui.classplans.create.preference.SelectPreferencesFragment;

public class CreateNewClassPlanFragmentContainer {
    public static final int MAX_STEPS = 2;

    public static boolean isLastStep(int step) {
        return step == MAX_STEPS - 1;
    }

    public Fragment getFragment(int step) {
        switch (step) {
            case 0:
                return new SelectAvailabilityFragment();
            case 2:
                return new SelectPreferencesFragment();
            default:
                throw new IllegalArgumentException("Invalid step: " + step);
        }
    }

}
