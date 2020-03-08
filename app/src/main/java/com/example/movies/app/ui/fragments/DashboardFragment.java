package com.example.movies.app.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.app.R;
import com.example.movies.app.OnClickFavoriteButtonIcon;
import com.example.movies.app.OnItemClickListener;
import com.example.movies.app.adapter.RecyclerAdapter;
import com.example.movies.app.models.Movie;
import com.example.movies.app.models.MovieComparator;
import com.example.movies.app.ui.acitivitys.DetailActivity;
import com.example.movies.app.ui.SharedViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

public class DashboardFragment extends Fragment implements OnItemClickListener, View.OnClickListener,
        OnClickFavoriteButtonIcon {
    private List<Movie> moviesData;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private SharedViewModel sharedViewModel;
    private Button buttonYear, buttonTitle, buttonRating;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_dashboard);
        buttonRating = view.findViewById(R.id.btn_rating);
        buttonTitle = view.findViewById(R.id.btn_title);
        buttonYear = view.findViewById(R.id.btn_year);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getMovies().observe(requireActivity(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesData = movies;
                initializeRecyclerView();
            }
        });

        Button[] buttons = {buttonYear, buttonTitle, buttonRating};
        for (Button button : buttons) {
            button.setOnClickListener(this);
        }
    }

    private void initializeRecyclerView() {
        adapter = new RecyclerAdapter(getContext(), sharedViewModel.getMovies().getValue(),
                this, this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickButtonIcon(Movie movie, int position) {
        movie.setFavorite(!movie.isFavorite());

        SharedPreferences sharedPref = getActivity().getPreferences(requireActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (movie.isFavorite()) {
            editor.putLong(String.valueOf(movie.getId()), movie.getId());
        } else {
            editor.remove(String.valueOf(movie.getId()));
        }

        editor.commit();

        adapter.notifyItemChanged(position);

        Snackbar.make(getView(), movie.isFavorite() ? getString(R.string.add_to_favorites)
                : getString(R.string.remove_from_favorites), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_DATA, movie);
        getContext().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        MovieComparator movieComparator = null;

        switch (v.getId()) {
            case R.id.btn_rating:
                movieComparator = MovieComparator.RATING;
                break;
            case R.id.btn_title:
                movieComparator = MovieComparator.TITLE;
                break;
            case R.id.btn_year:
                movieComparator = MovieComparator.YEAR;
                break;
        }

        for (int i = 0; i < moviesData.size(); i++) {
            moviesData.get(i).setMovieComparator(movieComparator);
        }
        Collections.sort(moviesData);
        adapter.notifyDataSetChanged(moviesData);
    }
}