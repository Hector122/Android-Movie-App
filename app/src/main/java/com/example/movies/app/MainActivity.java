package com.example.movies.app;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.movie.app.R;
import com.example.movies.app.ui.dashboard.DashboardFragment;
import com.example.movies.app.ui.favorites.FavoritesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener;
    // private MainViewModel mainViewModel;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();
        toolbar.setTitle(getString(R.string.title_dashboard));
        loadFragment(new DashboardFragment());

        onNavigationItemSelectedListener = this;

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav_view);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    /**
     * @param fragment
     */
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                loadFragment(fragment);
                toolbar.setTitle(getString(R.string.title_dashboard));
                return true;

            case R.id.navigation_favorite:
                fragment = new FavoritesFragment();
                loadFragment(fragment);
                toolbar.setTitle(getString(R.string.title_favorites));
                return true;
        }
        return false;
    }
}
