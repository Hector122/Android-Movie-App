package com.example.movies.app.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.app.models.Movie;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<Movie> movie;

    public LiveData<Movie> getMovies() {
        return movie;
    }
}
