package io.trishul.classplanner;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import io.trishul.classplanner.api.models.ClassPlanFilterRequest;
import io.trishul.classplanner.api.models.GradPlanFilterRequest;
import io.trishul.classplanner.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.trishul.classplanner.ui.base.BaseActivity;
import io.trishul.classplanner.ui.classplans.ClassPlansViewModel;
import io.trishul.classplanner.ui.filters.FilterGradPlansFragment;
import io.trishul.classplanner.ui.filters.FilterClassPlansFragment;
import io.trishul.classplanner.ui.filters.FilterClassesFragment;
import io.trishul.classplanner.ui.filters.FilterProfileFragment;
import io.trishul.classplanner.ui.gradplans.GradPlansViewModel;

public class MainActivity extends BaseActivity {

    public static final String EXTRA_DEFAULT_TAB = "defaultTab";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_filter); // Set the filter icon directly on the toolbar
        toolbar.setNavigationOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // Marking them as top-level destinations hide the back button on fragments
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.navigation_grad_plans,
            R.id.navigation_class_plans,
            R.id.navigation_classes,
            R.id.navigation_profile
        ).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Handle default tab selection and filters from intent
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.hasExtra(EXTRA_DEFAULT_TAB)) {
                int defaultTabId = intent.getIntExtra(EXTRA_DEFAULT_TAB, R.id.navigation_grad_plans);
                navController.navigate(defaultTabId);
                updateFiltersFromIntent(defaultTabId, intent);
            }
        }

        initializeFilterFragments(navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false; // No menu to inflate
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item); // No menu items to handle
    }

    private void initializeFilterFragments(NavController navController) {
        Fragment filterGradPlansFragment = new FilterGradPlansFragment();
        Fragment filterClassPlansFragment = new FilterClassPlansFragment();
        Fragment filterClassesFragment = new FilterClassesFragment();
        Fragment filterProfileFragment = new FilterProfileFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.filter_fragment_container, filterGradPlansFragment, "FilterGradPlans");
        transaction.add(R.id.filter_fragment_container, filterClassPlansFragment, "FilterClassPlans");
        transaction.add(R.id.filter_fragment_container, filterClassesFragment, "FilterClasses");
        transaction.add(R.id.filter_fragment_container, filterProfileFragment, "FilterProfile");
        transaction.addToBackStack(null);
        transaction.commit();

        Map<Integer, Fragment> fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.navigation_grad_plans, filterGradPlansFragment);
        fragmentMap.put(R.id.navigation_class_plans, filterClassPlansFragment);
        fragmentMap.put(R.id.navigation_classes, filterClassesFragment);
        fragmentMap.put(R.id.navigation_profile, filterProfileFragment);

        Map<Integer, Integer> titleMap = new HashMap<>();
        titleMap.put(R.id.navigation_grad_plans, R.string.toolbar_title_grad_plans);
        titleMap.put(R.id.navigation_class_plans, R.string.toolbar_title_class_plans);
        titleMap.put(R.id.navigation_classes, R.string.toolbar_title_classes);
        titleMap.put(R.id.navigation_profile, R.string.toolbar_title_profile);

        Toolbar toolbar = findViewById(R.id.top_bar);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Update toolbar title
            Integer titleId = titleMap.get(destination.getId());
            if (titleId != null) {
                toolbar.setTitle(getString(titleId));
            }
            
            // Handle filter fragment visibility
            Fragment selectedFragment = fragmentMap.get(destination.getId());
            if (selectedFragment != null) {
                for (Fragment fragment : fragmentMap.values()) {
                    fragmentManager
                            .beginTransaction()
                            .hide(fragment)
                            .commit();
                }
                fragmentManager.beginTransaction().show(selectedFragment).commit();
            }
        });
    }

    private void updateFiltersFromIntent(int tabId, Intent intent) {
        if (tabId == R.id.navigation_grad_plans) {
            GradPlansViewModel viewModel = new ViewModelProvider(this).get(GradPlansViewModel.class);
            GradPlanFilterRequest filter = viewModel.getCurrentFilter().getValue();
            
            if (intent.hasExtra("minCreditsRequired")) 
                filter.setMinCreditsRequired(intent.getIntExtra("minCreditsRequired", 0));
            if (intent.hasExtra("maxCreditsRequired"))
                filter.setMaxCreditsRequired(intent.getIntExtra("maxCreditsRequired", 0));
            if (intent.hasExtra("degree"))
                filter.setDegree(intent.getStringExtra("degree"));
            if (intent.hasExtra("major"))
                filter.setMajor(intent.getStringExtra("major"));
            if (intent.hasExtra("terms"))
                filter.setTerms(Arrays.asList(intent.getStringArrayExtra("terms")));
            
            viewModel.setCurrentFilter(filter);
            viewModel.setFiltersApplied(true);
            
        } else if (tabId == R.id.navigation_class_plans) {
            ClassPlansViewModel viewModel = new ViewModelProvider(this).get(ClassPlansViewModel.class);
            ClassPlanFilterRequest filter = viewModel.getCurrentFilter().getValue();
            
            if (intent.hasExtra("minCourses"))
                filter.setMinCourses(intent.getIntExtra("minCourses", 0));
            if (intent.hasExtra("maxCourses"))
                filter.setMaxCourses(intent.getIntExtra("maxCourses", 0));
            if (intent.hasExtra("burdenCapacity"))
                filter.setBurdenCapacity(Arrays.asList(intent.getStringArrayExtra("burdenCapacity")));
            if (intent.hasExtra("classDistribution"))
                filter.setClassDistribution(Arrays.asList(intent.getStringArrayExtra("classDistribution")));
            
            viewModel.setCurrentFilter(filter);
            viewModel.setFiltersApplied(true);
        }
    }
}