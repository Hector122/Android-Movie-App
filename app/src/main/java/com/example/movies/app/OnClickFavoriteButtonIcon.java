package com.example.movies.app;

import com.example.movies.app.models.Movie;

/**
 * When thw user click in any bottom for order the list.
 */
public interface OnClickFavoriteButtonIcon {
    void onClickButtonIcon(Movie movie, int position);
}
