package cz.johnyapps.adastraone_task.activities;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import cz.johnyapps.adastraone_task.R;
import cz.johnyapps.adastraone_task.databinding.ActivityMainBinding;

public abstract class BottomNavigationActivity extends BaseActivity {
    @Nullable
    private ActivityMainBinding binding;
    @Nullable
    private AppBarConfiguration appBarConfiguration;
    @Nullable
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));

        setContentView(binding.getRoot());
        setupToolbar();

        NavigationUI.setupActionBarWithNavController(this, getNavController(), getAppBarConfiguration());
        NavigationUI.setupWithNavController(binding.bottomNavigation, getNavController());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(toolbar, getNavController(), getAppBarConfiguration());
        setSupportActionBar(toolbar);
    }

    public abstract int[] getFragmentIds();

    @NonNull
    private AppBarConfiguration getAppBarConfiguration() {
        if (appBarConfiguration == null) {
            appBarConfiguration = new AppBarConfiguration.Builder(getFragmentIds()).build();
        }

        return appBarConfiguration;
    }

    @NonNull
    private NavController getNavController() {
        if (navController == null) {
            navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        }

        return navController;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Nullable
    public ActivityMainBinding getBinding() {
        return binding;
    }
}
