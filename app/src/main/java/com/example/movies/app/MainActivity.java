package com.example.movies.app;

import android.os.Bundle;
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
import com.example.movies.app.ui.SharedViewModel;
import com.example.movies.app.models.Movie;
import com.example.movies.app.ui.fragments.DashboardFragment;
import com.example.movies.app.ui.fragments.FragmentType;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static final String FRAGMENT_TYPE = "com.example.movie.app.FRAGMENT_TYPE";
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
        if (toolbar != null) {
            toolbar.setTitle(getString(R.string.title_dashboard));
        }

        //Set Default Fragment
        Bundle data = new Bundle();
        data.putString(FRAGMENT_TYPE, FragmentType.ALL_MOVIES.toString());

        Fragment fragment = new DashboardFragment();
        fragment.setArguments(data);
        loadFragment(fragment);

        BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = this;
        BottomNavigationView navigationView = findViewById(R.id.bottom_nav_view);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.navigation_dashboard:
                Bundle data = new Bundle();
                data.putString(FRAGMENT_TYPE, FragmentType.ALL_MOVIES.toString());

                fragment = new DashboardFragment();
                fragment.setArguments(data);
                loadFragment(fragment);

                toolbar.setTitle(getString(R.string.title_dashboard));
                return true;
            case R.id.navigation_favorite:
                Bundle bundle = new Bundle();
                bundle.putString(FRAGMENT_TYPE, FragmentType.FAVORITE_MOVIES.toString());

                fragment = new DashboardFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);

                toolbar.setTitle(getString(R.string.title_favorites));
                return true;
        }
        return false;
    }

    /**
     * Load the corresponding fragment to the container view.
     *
     * @param fragment Fragment to show.
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
