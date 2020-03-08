package com.example.movies.app.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.movie.app.R;
import com.example.movies.app.models.Movie;
import com.example.movies.app.ui.SharedViewModel;

import java.util.List;


public class FavoritesFragment extends Fragment {

    private SharedViewModel favoritesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        favoritesViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        favoritesViewModel.getMovies().observe(requireActivity(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {

            }
        });
        return view;
    }
}