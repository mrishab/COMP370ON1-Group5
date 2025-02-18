package io.trishul.classplanner.ui.courseplans.create;

import androidx.fragment.app.Fragment;

public class CreateNewCoursePlanFragmentContainer {
    public static final int MAX_STEPS = 2;

    public Fragment getFragment(int step) {
        switch (step) {
            case 0:
                return new UploadGradPlanFragment();
            default:
                throw new IllegalArgumentException("Invalid step: " + step);
        }
    }

}
