package com.example.movies.app.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.app.R;
import com.example.movies.app.SharedViewModel;
import com.example.movies.app.OnItemClickListener;
import com.example.movies.app.adapter.RecyclerAdapter;
import com.example.movies.app.models.Movie;
import com.example.movies.app.ui.detail.DetailActivity;

import java.util.List;

public class DashboardFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
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
                adapter.notifyDataSetChanged();
            }
        });
        
        Button[] buttons = {buttonYear, buttonTitle, buttonRating};
        for (Button button : buttons) {
            button.setOnClickListener(this);
        }

        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        adapter = new RecyclerAdapter(getContext(), sharedViewModel.getMovies().getValue(), this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("MOVIE", movie);
        getContext().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rating:

                break;
            case R.id.btn_title:

                break;
            case R.id.btn_year:

                break;
        }

        adapter.notifyDataSetChanged();
    }
}