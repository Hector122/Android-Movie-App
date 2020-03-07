package com.example.movies.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.android.volley.VolleyError;
import com.example.movies.app.models.Movies;
import com.example.movies.app.repositories.HttpVolleyClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.bottom_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_favorite).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    private void initializeRequest() {
        HttpVolleyClient.OnReposeReceiveData onReposeReceiveData = new HttpVolleyClient.OnReposeReceiveData() {
            @Override
            public void onReceiveData(String response) {
                Gson gson = new GsonBuilder().create();
                Movies movies = gson.fromJson(response, Movies.class);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        HttpVolleyClient.getInstance(this).getNowPlayingMoviesFromServer(onReposeReceiveData);
    }
}
