package io.trishul.classplanner.ui.courseplans.create;

import androidx.fragment.app.Fragment;

import io.trishul.classplanner.ui.courseplans.create.availability.SelectAvailabilityFragment;
import io.trishul.classplanner.ui.courseplans.create.upload.UploadGradPlanFragment;

public class CreateNewCoursePlanFragmentContainer {
    public static final int MAX_STEPS = 3;

    public Fragment getFragment(int step) {
        switch (step) {
            case 0:
                return new UploadGradPlanFragment();
            case 1:
                return new SelectAvailabilityFragment();
            default:
                throw new IllegalArgumentException("Invalid step: " + step);
        }
    }

}
