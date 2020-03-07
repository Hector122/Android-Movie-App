package com.example.movies.app.ui.dashboard;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.app.models.Movie;
import com.example.movies.app.repositories.HttpVolleyClient;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movies;
    private HttpVolleyClient client;

    void initialize(Context context) {
        if (movies != null) {
            return;
        }

        client = HttpVolleyClient.getInstance(context);
        movies = client.getNowPlayingMoviesFromServer();

       int size =  movies.getValue().size();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}