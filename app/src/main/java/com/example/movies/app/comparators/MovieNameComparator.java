package com.example.movies.app.comparators;

import com.example.movies.app.models.Movie;

public class MovieNameComparator extends  Movie implements Comparable<Movie> {

    @Override
    public int compareTo(Movie o) {
        return 0;
    }

    public int compare(Movie o1, Movie o2)
    {
        return o1.getOriginalTitle().compareTo(o2.getOriginalTitle());
    }
}
