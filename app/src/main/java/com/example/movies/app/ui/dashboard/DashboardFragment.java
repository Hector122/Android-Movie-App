package com.example.movies.app.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.app.R;
import com.example.movies.app.adapter.RecyclerAdapter;
import com.example.movies.app.models.Movie;

import java.util.List;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ProgressBar progressBar;

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_dashboard);
        progressBar = view.findViewById(R.id.progress_bar);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

        dashboardViewModel.initialize(getContext());

        dashboardViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.notifyDataSetChanged();
            }
        });

        initializeRecyclerView();

    }

    private void initializeRecyclerView() {
        List<Movie> testDelete = dashboardViewModel.getMovies().getValue();

        adapter = new RecyclerAdapter(getContext(), dashboardViewModel.getMovies().getValue());

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);

    }

    private void showProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        this.progressBar.setVisibility(View.INVISIBLE);
    }
}