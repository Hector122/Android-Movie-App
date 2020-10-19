package com.example.movies.app.models;

import java.util.Comparator;

/**
 * Used to set order to show in the list.
 */
public enum MovieComparator implements Comparator<Movie> {
    TITLE, YEAR, RATING;

    @Override
    public int compare(Movie o1, Movie o2) {
        switch (this) {
            case YEAR:
                return o1.getReleaseDate().compareTo(o2.getReleaseDate());

            case RATING:
                return o2.getRating().compareTo(o1.getRating());

            case TITLE:
            default:
                return o1.getOriginalTitle().compareTo(o2.getOriginalTitle());
        }
    }
}
