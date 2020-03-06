package com.example.movies.app.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MoviesViewModel extends ViewModel {
    private MutableLiveData<List<Movies>> movies;

    public LiveData<List<Movies>> getMovies() {
        return movies;
    }
}
