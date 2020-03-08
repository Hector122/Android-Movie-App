package com.example.movies.app;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.movie.app.R;
import com.example.movies.app.models.Movie;
import com.example.movies.app.ui.dashboard.DashboardFragment;
import com.example.movies.app.ui.favorites.FavoritesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener;
    private SharedViewModel sharedViewModel;
    private ProgressBar progressBar;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.initialize(getApplicationContext());
        sharedViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
               //TODO:
                int size = movies.size();
                Log.i("SIZE in Main: ", String.valueOf(size));
            }
        });

        sharedViewModel.isDownloadingMovies().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                if (value) {
                    showProgressBar();
                } else {
                    hideProgressBar();
                }
            }
        });

        toolbar = getSupportActionBar();
        toolbar.setTitle(getString(R.string.title_dashboard));
        loadFragment(new DashboardFragment());

        onNavigationItemSelectedListener = this;
        BottomNavigationView navigationView = findViewById(R.id.bottom_nav_view);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
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

    /**
     * @param fragment
     */
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    /**
     * Show progress bar in the view.
     */
    private void showProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hide progress bar in the view.
     */
    private void hideProgressBar() {
        this.progressBar.setVisibility(View.INVISIBLE);
    }
}
