package com.example.movies.app.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.app.models.Movies;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<List<Movies>> mMovies;

    public LiveData<List<Movies>> getMovies() {
        return mMovies;
    }
}