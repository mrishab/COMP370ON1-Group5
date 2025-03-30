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
import io.trishul.classplanner.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;
import androidx.core.view.GravityCompat;

public class MainActivity extends AppCompatActivity {

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
                R.id.navigation_profile)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false; // No menu to inflate
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item); // No menu items to handle
    }

}