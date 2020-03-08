package com.example.movies.app.ui.acitivitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.bumptech.glide.Glide;
import com.example.movie.app.R;
import com.example.movies.app.models.Movie;

public class DetailActivity extends AppCompatActivity {
    public static final String MOVIE_DATA = "com.example.movie-app";
    private ImageView imagePoster;
    private TextView rating, overview, movieTitle, releaseYear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagePoster = findViewById(R.id.image_movie_poster);
        releaseYear = findViewById(R.id.text_release_date);
        overview = findViewById(R.id.text_overview);
        movieTitle = findViewById(R.id.text_title);
        rating = findViewById(R.id.text_rating);

        initializeActivity();

    }

    private void initializeActivity() {
        Movie movie = getIntent().getParcelableExtra(MOVIE_DATA);

        if (movie != null) {
            rating.setText(movie.getRating());
            overview.setText(movie.getOverview());
            movieTitle.setText(movie.getOriginalTitle());
            releaseYear.setText(movie.getReleaseDate());

            Glide.with(DetailActivity.this)
                    .load(movie.getPosterUrlMediumSize())
                    .into(imagePoster);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                //NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


