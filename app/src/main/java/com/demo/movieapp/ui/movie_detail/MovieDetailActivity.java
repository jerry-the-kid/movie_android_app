package com.demo.movieapp.ui.movie_detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.demo.movieapp.R;
import com.demo.movieapp.adapter.CategoriesBtnAdapter;
import com.demo.movieapp.databinding.ActivityMovieDetailBinding;
import com.demo.movieapp.model.CategoryButton;
import com.demo.movieapp.model.GlobalState;
import com.demo.movieapp.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;

public class MovieDetailActivity extends AppCompatActivity {
    GlobalState globalState = GlobalState.getInstance();
    ActivityMovieDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        hideActionBar();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        binding.detailRecycledView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        CategoriesBtnAdapter categoriesBtnAdapter = new CategoriesBtnAdapter(new ArrayList<>(
                Arrays.asList(new CategoryButton("Horror", false),
                        new CategoryButton("Romance", false),
                        new CategoryButton("Comedy", false),
                        new CategoryButton("Crime", false))

        ));
        binding.detailRecycledView.setAdapter(categoriesBtnAdapter);
        binding.buttonPrev.setOnClickListener(v -> finish());

        Movie movie = globalState.getMovieList().get(0);
        binding.movieDetailTitle.setText(movie.getTitle());

    }


    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}