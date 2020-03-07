package com.example.movies.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Holder class
 */
public class MoviesHolder {
    @SerializedName("results")
    List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
