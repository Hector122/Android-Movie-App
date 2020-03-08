package com.example.movies.app.ui;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movie.app.R;
import com.example.movies.app.models.Movie;
import com.example.movies.app.repositories.HttpVolleyClient;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movies;
    private MutableLiveData<List<Movie>> favoriteMovies;
    private MutableLiveData<Boolean> downloadingMovies = new MediatorLiveData<>();

    public void initialize(Context context) {
        if (movies != null) {
            return;
        }
        downloadingMovies.setValue(true);

        HttpVolleyClient client = HttpVolleyClient.getInstance(context);
        movies = client.getNowPlayingMoviesFromServer();

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if (movies != null && movies.getValue() != null) {
            List<Movie> favorite = new ArrayList<>();

            for (Movie movie : movies.getValue()) {
                String value = String.valueOf(movie.getId());
                if (movie.getId() == sharedPref.getLong(value, 0)) {
                    favorite.add(movie);
                }
            }
            favoriteMovies = new MutableLiveData<>();
            favoriteMovies.postValue(favorite);
        }

        downloadingMovies.setValue(false);
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<List<Movie>> getfavoriteMovies() {
        return favoriteMovies;
    }

    public LiveData<Boolean> isDownloadingMovies() {
        return downloadingMovies;
    }
}
