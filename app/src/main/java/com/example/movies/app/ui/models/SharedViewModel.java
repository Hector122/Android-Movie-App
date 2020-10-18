package com.example.movies.app.ui.models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.app.models.Movie;
import com.example.movies.app.repositories.VolleyClient;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movies;
    private MutableLiveData<List<Movie>> favoriteMovies = new MediatorLiveData<>();
    private MutableLiveData<Boolean> downloadingMovies = new MediatorLiveData<>();

    /**
     * Set the MutableLive data with the notification.
     *
     * @param context Application Context.
     */
    public void initialize(Context context) {
        if (movies != null) {
            return;
        }
        downloadingMovies.setValue(true);

        VolleyClient client = VolleyClient.getInstance(context);

        movies = client.getNowPlayingMoviesFromServer();

        downloadingMovies.setValue(false);
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    /**
     * Add movies to the favorite list.
     *
     * @param movie movies.
     */
    public void addToFavoriteList(final Movie movie) {
        List<Movie> currentMovies = favoriteMovies.getValue();
        if (currentMovies == null) {
            currentMovies = new ArrayList<>();
        }

        currentMovies.add(movie);
        favoriteMovies.postValue(currentMovies);
    }

    /**
     * Remove movies from the favorite list.
     *
     * @param movie movies.
     */
    public void removeFromFavoriteList(final Movie movie) {
        List<Movie> currentMovies = favoriteMovies.getValue();
        if (currentMovies != null) {
            currentMovies.remove(movie);
            favoriteMovies.postValue(currentMovies);
        }
    }


    public LiveData<Boolean> isDownloadingMovies() {
        return downloadingMovies;
    }
}
