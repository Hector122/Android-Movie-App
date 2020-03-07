package com.example.movies.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.app.R;
import com.example.movies.app.models.Movies;

import java.util.List;

/**
 * Adapter tho show the movie content
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Movies> mMoviesDataSource;
    private Context mContext;


    public RecyclerAdapter(Context context, List<Movies> moviesDataSource) {
        this.mContext = context;
        this.mMoviesDataSource = moviesDataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMoviesDataSource.size();
    }

    /**
     * Provide a reference to the views for each data item
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePoster;
        Button buttonFavorite;

        ViewHolder(View view) {
            super(view);

            imagePoster = view.findViewById(R.id.image_movie);
            buttonFavorite = view.findViewById(R.id.button_favorites);
        }
    }
}
