package com.example.movies.app.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.movies.app.OnClickFavoriteButtonIcon;
import com.example.movies.app.OnItemClickListener;
import com.example.movies.app.adapter.RecyclerAdapter;
import com.example.movies.app.models.Movie;
import com.example.movies.app.models.MovieComparator;
import com.example.movies.app.ui.acitivitys.DetailActivity;
import com.example.movies.app.ui.SharedViewModel;

import java.util.Collections;
import java.util.List;

public class DashboardFragment extends Fragment implements OnItemClickListener, View.OnClickListener,
        OnClickFavoriteButtonIcon {
    private List<Movie> moviesData;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private SharedViewModel sharedViewModel;
    private Button buttonYear, buttonTitle, buttonRating;
    private TextView textEmptyMessage;
    private FragmentType fragmentType;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_dashboard);
        textEmptyMessage = view.findViewById(R.id.text_notifications);
        buttonRating = view.findViewById(R.id.btn_rating);
        buttonTitle = view.findViewById(R.id.btn_title);
        buttonYear = view.findViewById(R.id.btn_year);

        String valor = getArguments() != null ? getArguments().getString("X") : FragmentType.FAVORITE_MOVIES.toString();
        fragmentType = FragmentType.valueOf(valor);


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

    private void initializeRecyclerView() {
        if (moviesData == null || moviesData.isEmpty()) {
            textEmptyMessage.setVisibility(View.VISIBLE);
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

        SharedPreferences sharedPref = getActivity().getPreferences(requireActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (movie.isFavorite()) {
            editor.putLong(String.valueOf(movie.getId()), movie.getId());
            sharedViewModel.addToFavoriteList(movie);
        } else {
            editor.remove(String.valueOf(movie.getId()));
            sharedViewModel.removeFromFavoriteList(movie);
        }

        editor.commit();

        adapter.notifyItemChanged(position);

//        Snackbar.make(getView().findViewById(R.id.coordinator_snack_bar), movie.isFavorite() ? getString(R.string.add_to_favorites)
//                : getString(R.string.remove_from_favorites), Snackbar.LENGTH_SHORT).show();
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