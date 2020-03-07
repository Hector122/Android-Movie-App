package com.example.movies.app.ui.detail;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.movie.app.R;
import com.example.movies.app.models.Movie;

public class DetailActivity extends AppCompatActivity {
    private ImageView imagePoster;
    private TextView rating, overview, movieTitle, releaseYear;
    private DetailViewModel detailViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_detail);

        imagePoster = findViewById(R.id.image_movie_poster);
        releaseYear = findViewById(R.id.text_release_date);
        overview = findViewById(R.id.text_overview);
        movieTitle = findViewById(R.id.text_title);
        rating = findViewById(R.id.text_rating);


        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        detailViewModel.getMovies().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {

            }
        });

        initalizeView();
    }

    private void initalizeView(){
        Movie movie = detailViewModel.getMovies().getValue();

    }


}


