package com.example.movies.app.ui;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.app.models.Movie;
import com.example.movies.app.repositories.HttpVolleyClient;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movies;
    private MutableLiveData<Boolean> downloadingMovies = new MediatorLiveData<>();

   public void initialize(Context context) {
        if (movies != null) {
            return;
        }
        downloadingMovies.setValue(true);

        HttpVolleyClient client = HttpVolleyClient.getInstance(context);
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
