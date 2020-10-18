package com.example.movies.app.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.app.R;
import com.example.movies.app.MainActivity;
import com.example.movies.app.OnClickFavoriteButtonListener;
import com.example.movies.app.OnItemClickListener;
import com.example.movies.app.adapter.RecyclerAdapter;
import com.example.movies.app.models.Movie;
import com.example.movies.app.models.MovieComparator;
import com.example.movies.app.ui.models.SharedViewModel;
import com.example.movies.app.ui.acitivitys.DetailActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

/**
 * View that show the list of all movies.
 */
public class DashboardFragment extends Fragment implements OnItemClickListener, View.OnClickListener,
        OnClickFavoriteButtonListener {
    private List<Movie> moviesData;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private SharedViewModel sharedViewModel;
    private Button buttonYear, buttonTitle, buttonRating;
    private TextView textEmptyMessage;
    private FragmentType fragmentType;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_dashboard);
        textEmptyMessage = view.findViewById(R.id.text_notifications);
        buttonRating = view.findViewById(R.id.btn_rating);
        buttonTitle = view.findViewById(R.id.btn_title);
        buttonYear = view.findViewById(R.id.btn_year);

        if (getArguments() != null) {
            fragmentType = FragmentType.valueOf((String) getArguments().get(MainActivity.FRAGMENT_TYPE));
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        if (fragmentType == FragmentType.FAVORITE_MOVIES) {
            sharedViewModel.getFavoriteMovies().observe(requireActivity(), new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> movies) {
                    moviesData = movies;
                    initializeRecyclerView();
                }
            });
        } else {
            sharedViewModel.getMovies().observe(requireActivity(), new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> movies) {
                    moviesData = movies;
                    initializeRecyclerView();
                }
            });
        }
    }

    /**
     * Initializer the recycler view.
     */
    private void initializeRecyclerView() {
        if (moviesData == null) {
            textEmptyMessage.setVisibility(View.VISIBLE);
            textEmptyMessage.setText(getString(R.string.no_favorite));
            return;
        }

        adapter = new RecyclerAdapter(getContext(), moviesData,
                this, this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Button[] buttons = {buttonYear, buttonTitle, buttonRating};
        for (Button button : buttons) {
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Button[] buttons = {buttonYear, buttonTitle, buttonRating};
        for (Button button : buttons) {
            button.setOnClickListener(null);
        }
    }

    @Override
    public void onClickButtonIcon(Movie movie, int position) {
        movie.setFavorite(!movie.isFavorite());

        if (movie.isFavorite()) {
            sharedViewModel.addToFavoriteList(movie);
        } else {
            sharedViewModel.removeFromFavoriteList(movie);
        }

        adapter.notifyDataSetChanged();

        Snackbar.make(getActivity().findViewById(R.id.container), movie.isFavorite() ? getString(R.string.add_to_favorites)
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

        if (moviesData != null) {
            Collections.sort(moviesData, movieComparator);
            adapter.notifyDataSetChanged(moviesData);
        }
    }
}