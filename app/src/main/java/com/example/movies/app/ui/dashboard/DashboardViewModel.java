package com.example.movies.app.ui.dashboard;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.app.models.Movie;
import com.example.movies.app.repositories.HttpVolleyClient;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movies;
    private HttpVolleyClient client;
    private MutableLiveData<Boolean> downloadingMovies = new MediatorLiveData<>();

    void initialize(Context context) {
        if (movies != null) {
            return;
        }

        downloadingMovies.setValue(true);

        client = HttpVolleyClient.getInstance(context);
        movies = client.getNowPlayingMoviesFromServer();

        downloadingMovies.setValue(false);
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> isDownloadingMovies() {
        return downloadingMovies;
    }
}