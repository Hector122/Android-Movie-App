package com.example.movies.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Movie Holder class
 */
public class Movie implements Parcelable, Comparable<Movie> {
    @SerializedName("id")
    private long id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("poster_path")
    private String posterUrl;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_average")
    private String rating;

    private boolean favorite;
    private MovieComparator movieComparator;

    public Movie() {
        movieComparator = MovieComparator.TITLE;
    }

    public long getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterUr() {
        return "https://image.tmdb.org/t/p/w154/" + posterUrl;
    }

    public String getPosterUrlMediumSize() {
        return "https://image.tmdb.org/t/p/w342/" + posterUrl;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean value) {
        this.favorite = value;
    }

    public MovieComparator getMovieComparator() {
        return movieComparator;
    }

    public void setMovieComparator(MovieComparator movieComparator) {
        this.movieComparator = movieComparator;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalTitle);
        dest.writeString(this.posterUrl);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.rating);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
    }

    protected Movie(Parcel in) {
        this.originalTitle = in.readString();
        this.posterUrl = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.rating = in.readString();
        this.favorite = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int compareTo(Movie o) {
        switch (movieComparator) {
            case YEAR:
                return this.getReleaseDate().compareTo(o.getReleaseDate());

            case RATING:
                return this.getRating().compareTo(o.getRating());

            case TITLE:
            default:
                return this.getOriginalTitle().compareTo(o.getOriginalTitle());
        }
    }
}
