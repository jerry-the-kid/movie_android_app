package com.demo.movieapp.ui.movie_detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.demo.movieapp.R;
import com.demo.movieapp.adapter.CategoriesBtnAdapter;
import com.demo.movieapp.databinding.ActivityMovieDetailBinding;
import com.demo.movieapp.model.CategoryButton;
import com.google.android.gms.common.util.DataUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class MovieDetailActivity extends AppCompatActivity {
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
    }


    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}