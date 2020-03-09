package com.example.movies.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movie.app.R;
import com.example.movies.app.OnClickFavoriteButtonIcon;
import com.example.movies.app.OnItemClickListener;
import com.example.movies.app.models.Movie;

import java.util.List;

/**
 * Adapter tho show the movie content
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movieDataSource;
    private OnItemClickListener onItemClickListener;
    private OnClickFavoriteButtonIcon onClickFavoriteButtonIcon;

    public RecyclerAdapter(Context context, List<Movie> movieDataSource,
                           OnItemClickListener onItemClickListener,
                           OnClickFavoriteButtonIcon onClickFavoriteButtonIcon) {
        this.context = context;
        this.movieDataSource = movieDataSource;
        this.onItemClickListener = onItemClickListener;
        this.onClickFavoriteButtonIcon = onClickFavoriteButtonIcon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Movie movie = movieDataSource.get(position);

        Glide.with(context)
                .load((movie.getPosterUr()))
                .apply(new RequestOptions().fitCenter())
                .into(holder.imagePoster);

        holder.rating.setText(movie.getRating());

        int imageResource = movie.isFavorite() ? R.drawable.ic_favorite_black_24dp
                : R.drawable.ic_favorite_border_24px;
        holder.buttonFavorite.setBackgroundResource(imageResource);

        // set the image.
        holder.buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFavoriteButtonIcon.onClickButtonIcon(movie, position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(movie);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieDataSource.size();
    }

    /**
     * Notify that data set has changed
     *
     * @param dataSource List with the new data to show.
     */
    public void notifyDataSetChanged(final List<Movie> dataSource) {
        movieDataSource = dataSource;
        this.notifyDataSetChanged();
    }

    /**
     * Provide a reference to the views for each data item
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePoster;
        Button buttonFavorite;
        TextView rating;

        ViewHolder(View view) {
            super(view);

            imagePoster = view.findViewById(R.id.image_movie);
            buttonFavorite = view.findViewById(R.id.button_favorites);
            rating = view.findViewById(R.id.text_rating);
        }
    }
}
